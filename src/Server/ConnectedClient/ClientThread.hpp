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

#ifndef _CLIENTTHREAD_HPP_
#define _CLIENTTHREAD_HPP_

class ClientThread;

#include "Context.hpp"

/*!
 * The ClientThread class provides the server with thread methods
 * for working with client.
 */
class ClientThread
{
public:
    /*!
     * \brief run (to be run in a thread) executes the handling of client with given context.
     * \param context is the given client's context.
     */
    static void run(std::shared_ptr<Context> context);
private:
    /*!
     * \brief communicate starts the network communication with the given client context.
     * \param context is the give client's context.
     */
    static void communicate(std::shared_ptr<Context> context);
};

#endif
