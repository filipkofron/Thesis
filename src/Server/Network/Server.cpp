#include "Server.hpp"
#include "ServerException.hpp"
#include "../Config/Configurator.hpp"
#include "../Client/ClientThread.hpp"
#include <sys/socket.h>
#include <cstdio>
#include <thread>
#include <mutex>
#include <cstdlib>
#include <sys/ioctl.h>
#include <sys/time.h>
#include <netinet/in.h>
#include <errno.h>
#include <unistd.h>
#include <cstring>
#include <iostream>

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

    std::cout << "DEBUG: server will timeout after 20minutes!" << std::endl;
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

    std::cout << "Started server on port " << port << std::endl;
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

        std::cout << "Accept res: " << clientSD << std::endl;

        if (clientSD < 0)
        {
            std::cout << "Time out.." << std::endl;
            continue;
            //stopped = true;
        }
        else
        {
            handleClient(clientSD, clientAddr);
        }

        clearFinishedContexts();
    }

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
        std::cout << "Cleaning up client..." << std::endl;
        std::cout.flush();
        std::shared_ptr<Context> context = finishedContexts.back();
        finishedContexts.pop_back();

        auto it = clients.find(context->getClientSD());

        if(it != clients.end())
        {
            clients.erase(it);
        }

        context->getThread()->join();

        std::cout << "Client cleaned up!" << std::endl;
    }
}

const uint64_t &Server::getClientNum()
{
    return clientNum;
}
