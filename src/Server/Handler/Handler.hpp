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

#ifndef _HANDLER_HPP_
#define _HANDLER_HPP_

class Handler;

#include "../ConnectedClient/Context.hpp"

/*!
 * The Handler is an abstract class defining basic Hanfler interface.
 * A handler is responsible for handling a specific requestfro the server.
 * A handler is responsible for carrying the request data from its creation.
 */
class Handler
{
public:
    virtual ~Handler(); //!< virual destructor so that childs can be called as well
    
    /*!
     * handle method is called by the client thread to handle a request.
     * The method is given the client context
     */
    virtual void handle(Context &context) = 0;
};


#endif
