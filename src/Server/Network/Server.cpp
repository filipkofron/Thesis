/*
Copyright (C) 2014 Filip Kofron (filip.kofron.cz@gmail.com)

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

#include "Server.hpp"
#include "ServerException.hpp"
#include "../Config/Configurator.hpp"
#include "../ConnectedClient/ClientThread.hpp"
#include <cstdio>
#include <thread>
#include <mutex>
#include <cstdlib>
#include <sys/ioctl.h>
#include <sys/time.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <errno.h>
#include <unistd.h>
#include <cstring>
#include "../Util/Log.hpp"

Server::Server()
    : stopped(false), clientNum(0)
{
}

void Server::initialize()
{
    listenSD = socket(AF_INET, SOCK_STREAM, 0);
    if (listenSD < 0)
    {
        throw ServerException("Cannot create listen socket!");
    }

    int rc = 0, on = 1;

    rc = setsockopt(listenSD, SOL_SOCKET,  SO_REUSEADDR, (char *) &on, sizeof(on));
    if (rc < 0)
    {
       close(listenSD);
       throw ServerException("setsockopt() failed!");
    }

    int port = Configurator::getInstance()->getNetworkServerListenPort();

    memset(&addr, 0, sizeof(addr));
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = htonl(INADDR_ANY);
    addr.sin_port = htons(port);
    rc = bind(listenSD, (struct sockaddr *)&addr, sizeof(addr));
    if (rc < 0)
    {
       close(listenSD);
       throw ServerException("bind() failed!");
    }

    acceptTimeout.tv_sec  = 20 * 60;
    acceptTimeout.tv_usec = 0;

    rc = setsockopt (listenSD, SOL_SOCKET, SO_RCVTIMEO, (char *) &acceptTimeout, sizeof(acceptTimeout));
    if(rc < 0)
    {
        close(listenSD);
        throw ServerException("setsockopt() failed!");
    }

    rc = listen(listenSD, Configurator::getInstance()->getNetworkServerBackLog());
    if (rc < 0)
    {
       close(listenSD);
       throw ServerException("listen() failed!");
    }

    Log::info(std::string("Started server on port ") + std::to_string(port));
}

void Server::addrToStr(char *buffer, int bufLen, const sockaddr_in &addr)
{
    const char *ad = (const char *) &addr.sin_addr;

    snprintf(buffer, bufLen, "%d.%d.%d.%d", ((int) ad[0]) & 0xFF, ((int) ad[1]) & 0xFF, ((int) ad[2]) & 0xFF, ((int) ad[3]) & 0xFF);
}

void Server::handleClient(int clientSD, sockaddr_in clientAddr)
{
    std::lock_guard<std::mutex> lock(clientMutex);
    std::shared_ptr<Context> context(new Context());

    timeval clientTimeout;

    clientTimeout.tv_sec  = 45;
    clientTimeout.tv_usec = 0;

    setsockopt (clientSD, SOL_SOCKET, SO_RCVTIMEO, (char *) &clientTimeout, sizeof(clientTimeout));
    setsockopt (clientSD, SOL_SOCKET, SO_SNDTIMEO, (char *) &clientTimeout, sizeof(clientTimeout));

    context->setServer(this);
    context->setClientSD(clientSD);
    addrToStr(context->getAddr(), CONTEXT_ADDR_BUFFER_LEN, clientAddr);
    context->setPort((int) clientAddr.sin_port);

    clients[clientSD] = context;
    context->setThread(std::shared_ptr<std::thread> (new std::thread(ClientThread::run, context)));
    clientNum++;
}

void Server::run()
{
    sockaddr_in clientAddr;
    socklen_t clientAddrSize = (socklen_t) sizeof(sockaddr_in);
    while(!stopped)
    {
        int clientSD = accept(listenSD, (sockaddr *) &clientAddr, &clientAddrSize);

        if (clientSD < 0)
        {
            continue;
        }
        else
        {
            handleClient(clientSD, clientAddr);
        }

        clearFinishedContexts();
    }

    Log::info("Server stopped.");

    close(listenSD);
}

void Server::setStopped(const bool &stopped)
{
    this->stopped = stopped;
}

std::mutex &Server::getClientMutex()
{
    return clientMutex;
}

std::map<int, std::shared_ptr<Context>> &Server::getClients()
{
    return clients;
}

std::deque<std::shared_ptr<Context>> &Server::getFinishedContexts()
{
    return finishedContexts;
}

void Server::clearFinishedContexts()
{
    std::lock_guard<std::mutex> lock(clientMutex);

    while(finishedContexts.size() > 0)
    {
        std::shared_ptr<Context> context = finishedContexts.back();
        finishedContexts.pop_back();

        auto it = clients.find(context->getClientSD());

        if(it != clients.end())
        {
            clients.erase(it);
        }

        context->getThread()->join();
    }
}

const uint64_t &Server::getClientNum()
{
    return clientNum;
}
