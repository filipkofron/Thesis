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

#ifndef _MESSAGESENDER_HPP_
#define _MESSAGESENDER_HPP_

class MessageSender;

#include "../Protocol/Message.hpp"
#include "../ConnectedClient/Context.hpp"

/*!
 * The MessageSender class provides sending of a message to a given client's socket
 * from a given client's context.
 */
class MessageSender
{
public:
    /*!
     * \brief sendMessage sends the given message
     * \param context client's context
     * \param message message to be sent
     */
    static void sendMessage(Context &context, Message &message);
};

#endif

