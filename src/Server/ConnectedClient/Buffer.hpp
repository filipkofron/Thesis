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

#ifndef _BUFFER_HPP_
#define _BUFFER_HPP_

class Buffer;

#include <cstdlib>
/*!
 * The default length of the buffer.
 */
#define BUFFER_DEFAULT_LEN 1024

/*!
 * Buffer class encapulates bytes in an internal array and enables
 * a few useful functions.
 */
class Buffer
{
private:
    size_t size; //!< Current size of the data inside the buffer.
    int position; //!< Current position inside the buffer.
    size_t maxSize; //!< Current size of the allocated memory.
    char *bytes; //!< Backing array.
public:

    Buffer(); //!< Allocated the default length of the data.
    ~Buffer(); //!< Frees the backing array.

    void reset(); //!< sets the position to 0.
    void skip(int bytes); //!< moves the position specified number of bytes forward.
    const int &getPosition(); //!< Retrieves the position.
    void prepare(size_t size); //<! Prepares the buffer for specified size, enlarging maxSize if needed.
    char *getBytes(); //<! Retrieves the raw backing array.
};

#endif
