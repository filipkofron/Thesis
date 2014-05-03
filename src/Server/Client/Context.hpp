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

#ifndef _CLIENTCONTEXT_HPP_
#define _CLIENTCONTEXT_HPP_

class Context;

#include <thread>
#include <array>
#include <memory>
#include "Buffer.hpp"
#include "../Network/Server.hpp"

#define CONTEXT_ADDR_BUFFER_LEN 16

class Context
{
private:
    char addr[CONTEXT_ADDR_BUFFER_LEN];
    int port;
    bool finished;
    int clientSD;
    bool loggedIn;
    std::string username;
    int userId;
    Buffer buffer;
    Server *server;
    std::shared_ptr<std::thread> thread;
public:
    Context();
    void setClientSD(const int &clientSD);
    const int &getClientSD();
    Buffer &getBuffer();
    void setServer(Server *server);
    Server *getServer();
    void setThread(std::shared_ptr<std::thread> thread);
    std::shared_ptr<std::thread> getThread();
    char *getAddr();
    void setPort(const int &port);
    const int &getPort();
    const bool &getFinished();
    void setFinished(const bool &finished);
    const bool &getLoggedIn();
    void setLoggedIn(const bool &loggedIn);
    const std::string &getUsername();
    void setUsername(const std::string &username);
    const int &getUserId();
    void setUserId(const int &userId);
};

#endif
