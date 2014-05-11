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

#ifndef _BUFFERWRITER_HPP_
#define _BUFFERWRITER_HPP_

class BufferWriter;

#include "../ConnectedClient/Buffer.hpp"
#include "../ConnectedClient/Context.hpp"

/*!
 * The BufferWriter class provides writing of a client buffer.
 */
class BufferWriter
{
private:
    /*!
     * \brief writeSize writes the size to the client's data stream
     * \param sd client's stream socket descriptor
     *
     * WriteException is raised on error.
     */
    static void writeSize(const int &sd, const int &size);

    /*!
     * \brief writeFully writes exact amount of bytes from a given array of bytes to socket
     * \param sd socket descriptor
     * \param buffer buffer's backing array
     * \param len number of bytes to write
     *
     * ReadException is raised upon error.
     */
    static void writeFully(const int &sd, const char *buffer, const int &len);
public:
    /*!
     * \brief writeBuffer writes buffer for the given client's context
     * \param context the given client context
     */
    static void writeBuffer(Context &context);
};

#endif
