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

#ifndef _BUFFERREADER_HPP_
#define _BUFFERREADER_HPP_

class BufferReader;

#include "../ConnectedClient/Buffer.hpp"
#include "../ConnectedClient/Context.hpp"

#include <memory>

/*!
 * The BufferReader class provides reading of a client buffer.
 */
class BufferReader
{
private:
    /*!
     * \brief readSize reads the size from the client's data stream
     * \param sd client's stream socket descriptor
     * \return the read size
     *
     * ReadException is raised on error.
     */
    static int readSize(const int &sd);

    /*!
     * \brief readFully reads exact amount of bytes from a socket to given array of bytes
     * \param sd socket descriptor
     * \param buffer buffer's backing array
     * \param len number of bytes to read
     *
     * ReadException is raised upon error.
     */
    static void readFully(const int &sd, char *buffer, const int &len);
public:
    /*!
     * \brief readBuffer reads buffer for the given client's context
     * \param context the given client context
     */
    static void readBuffer(Context &context);
};

#endif
