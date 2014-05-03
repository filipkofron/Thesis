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

#ifndef _SERVER_HPP_
#define _SERVER_HPP_

class Server;

#include <map>
#include <netinet/in.h>
#include <memory>
#include <mutex>
#include <ctime>
#include <deque>
#include <inttypes.h>

#include "../Client/Context.hpp"

class Server
{
private:
    std::map<int, std::shared_ptr<Context>> clients;
    std::deque<std::shared_ptr<Context>> finishedContexts;
    std::mutex clientMutex;
    int listenSD;
    bool stopped;
    sockaddr_in addr;
    timeval acceptTimeout;
    uint64_t clientNum;
    void addrToStr(char *buffer, int bufLen, const sockaddr_in &addr);
    void handleClient(int clientSD, sockaddr_in clientAddr);
    void clearFinishedContexts();
public:
    Server();
    void initialize();
    void run();
    void setStopped(const bool &stopped);
    std::mutex &getClientMutex();
    std::map<int, std::shared_ptr<Context>> &getClients();
    std::deque<std::shared_ptr<Context>> &getFinishedContexts();
    const uint64_t &getClientNum();
};

#endif
