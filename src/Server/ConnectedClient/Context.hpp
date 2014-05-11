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

/*!
 * The Context class is a holder class for a connected client's context.
 */
class Context
{
private:
    char addr[CONTEXT_ADDR_BUFFER_LEN]; //!< client's address info
    int port; //!< the client's port number
    bool finished; //!< the communication with client finished
    int clientSD; //!< client's socket descriptor
    bool loggedIn; //!< whether the client is logged in
    std::string username; //!< client's username (available when logged in)
    int userId; //!< client's user id (available when logged in)
    Buffer buffer; //!< the client's communication buffer
    Server *server; //!< the pointer to server object, for releasing itself only
    std::shared_ptr<std::thread> thread; //!< the client's handling thread
public:
    Context(); //!< initializes vars
    void setClientSD(const int &clientSD); //!< sets the client's socket descriptor
    const int &getClientSD(); //!< retrieves the client's socket descriptor
    Buffer &getBuffer(); //!< retrieves the buffer
    void setServer(Server *server); //!< sets the pointer to server object
    Server *getServer(); //!< retrieves the pointer to server object
    void setThread(std::shared_ptr<std::thread> thread); //!< sets the smart ptr to client's handling thread
    std::shared_ptr<std::thread> getThread(); //!< retrieves the smart ptr to client's handling thread
    char *getAddr(); //!< retrieves the client's addr binary data
    void setPort(const int &port); //!< sets the client's port number
    const int &getPort(); //!< retrieves the client's port number
    const bool &getFinished(); //!< retrieves whether the client has finished yet
    void setFinished(const bool &finished); //!< sets the finished state for client
    const bool &getLoggedIn(); //!< retrieves whether the client is logged in
    void setLoggedIn(const bool &loggedIn); //!< sets when the client is logged in
    const std::string &getUsername(); //!< retrieves the client's username
    void setUsername(const std::string &username); //!< sets the client's username
    const int &getUserId(); //!< retrieves the client's user id
    void setUserId(const int &userId); //!< sets retrieves the client's user id
};

#endif
