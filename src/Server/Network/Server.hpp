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

#include "../ConnectedClient/Context.hpp"

/*!
 * The Server class encapsulates basic socket server with handling of multiple clients using threads.
 */
class Server
{
private:
    std::map<int, std::shared_ptr<Context>> clients; //!< map of clients mapped by their socket descriptors
    std::deque<std::shared_ptr<Context>> finishedContexts; //!< contexts from clients that have already finished (are safely disconnected)
    std::mutex clientMutex; //!< mutex for handling clients
    int listenSD; //!< socket descriptor for server to listen for clients
    bool stopped; //!< when set, the server will shutdown at the next timeout
    sockaddr_in addr; //!< server's address to bind to
    timeval acceptTimeout; //!< timeout for accept loop
    uint64_t clientNum; //!< the number of clients connected during the whole runtime
    void addrToStr(char *buffer, int bufLen, const sockaddr_in &addr); //!< disambless the binary format of the address to readable string
    void handleClient(int clientSD, sockaddr_in clientAddr); //!< called upon accepting a client and gathering its address
    void clearFinishedContexts(); //!< clears all finished clients' contexts
public:
    Server(); //!< initializes member variables
    void initialize(); //!< initializes the server (binds the socket)
    void run(); //!< runs the accepting loop (blocking until the server is shutdown)
    void setStopped(const bool &stopped); //!< sets the server to stop state, which will cause the server to shutdown upon next accept timeout
    std::mutex &getClientMutex(); //!< retrieves the mutex to handle clients, needed in the client's thread to return the finished context
    std::map<int, std::shared_ptr<Context>> &getClients(); //!< retrieves all clients map
    std::deque<std::shared_ptr<Context>> &getFinishedContexts(); //!< retrieves the deque of all finished contexts
    const uint64_t &getClientNum(); //!< retrieves the number of clients connected during the whole server runtime
};

#endif
