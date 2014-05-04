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

#include "Buffer.hpp"

#include "../Config/Configurator.hpp"
#include "BufferException.hpp"

Buffer::Buffer()
    : size(BUFFER_DEFAULT_LEN), position(0),
      maxSize((size_t) Configurator::getInstance()->getNetworkClientMaxBuffer())
{
    bytes = (char *) malloc(size);
}

Buffer::~Buffer()
{
    free(bytes);
}

void Buffer::prepare(size_t target)
{
    if(target > size)
    {
        if(target >= maxSize)
        {
            throw BufferException("Buffer attempted to allocate more than MAX!");
        }
        bytes = (char *) realloc(bytes, target);
        size = target;
    }
}

void Buffer::reset()
{
    position = 0;
}

void Buffer::skip(int bytes)
{
    position += bytes;
    position %= size;
}

const int &Buffer::getPosition()
{
    return position;
}

char *Buffer::getBytes()
{
    return bytes;
}
