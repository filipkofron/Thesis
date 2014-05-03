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

#include "BufferWriter.hpp"
#include "WriteException.hpp"
#include <cstring>
#include <unistd.h>

void BufferWriter::writeSize(const int &sd, const int &size)
{
    unsigned char buffer[4];

    buffer[0] = ((size >> 24) & 0xFF);
    buffer[1] = ((size >> 16) & 0xFF);
    buffer[2] = ((size >> 8) & 0xFF);
    buffer[3] = (size & 0xFF);

    writeFully(sd, (char *) buffer, sizeof(buffer));
}

void BufferWriter::writeFully(const int &sd, const char *buffer, const int &len)
{
    int wroteAlready = 0;

    while(len - wroteAlready)
    {
        int ret = write(sd, buffer + wroteAlready, len - wroteAlready);

        if(ret <= 0 || ret > (len - wroteAlready))
        {
            throw WriteException();
        }
        wroteAlready += ret;
    }
}

void BufferWriter::writeBuffer(Context &context)
{
    /**
     * @todo this won't work with compressed buffer.
     */
    int lenBytes = strlen(context.getBuffer().getBytes());

    writeSize(context.getClientSD(), lenBytes);

    writeFully(context.getClientSD(), context.getBuffer().getBytes(), lenBytes);
}
