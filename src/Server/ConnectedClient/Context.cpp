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

#include "Context.hpp"

Context::Context()
    : finished(false), loggedIn(false), username("_no_user_")
{
}

void Context::setClientSD(const int &clientSD)
{
    this->clientSD = clientSD;
}

const int &Context::getClientSD()
{
    return clientSD;
}

Buffer &Context::getBuffer()
{
    return buffer;
}

void Context::setServer(Server *server)
{
    this->server = server;
}

Server *Context::getServer()
{
    return server;
}

void Context::setThread(std::shared_ptr<std::thread> thread)
{
    this->thread = thread;
}

std::shared_ptr<std::thread> Context::getThread()
{
    return thread;
}

char *Context::getAddr()
{
    return addr;
}

void Context::setPort(const int &port)
{
    this->port = port;
}

const int &Context::getPort()
{
    return port;
}

const bool &Context::getFinished()
{
    return finished;
}

void Context::setFinished(const bool &finished)
{
    this->finished = finished;
}

const bool &Context::getLoggedIn()
{
    return loggedIn;
}

void Context::setLoggedIn(const bool &loggedIn)
{
    this->loggedIn = loggedIn;
}

const std::string &Context::getUsername()
{
    return username;
}

void Context::setUsername(const std::string &username)
{
    this->username = username;
}

const int &Context::getUserId()
{
    return userId;
}

void Context::setUserId(const int &userId)
{
    this->userId = userId;
}
