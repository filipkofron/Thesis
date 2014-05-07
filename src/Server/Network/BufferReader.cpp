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

#include "BufferReader.hpp"
#include "ReadException.hpp"
#include <unistd.h>

void BufferReader::readFully(const int &sd, char *buffer, const int &len)
{
    int readAlready = 0;

    while(len - readAlready)
    {
        int ret = read(sd, buffer + readAlready, len - readAlready);
        if(ret <= 0 || ret > (len - readAlready))
        {
            throw ReadException();
        }
        readAlready += ret;
    }
}

int BufferReader::readSize(const int &sd)
{
    unsigned char buffer[4];

    readFully(sd, (char *) buffer, sizeof(buffer));

    int size = 0;
    size += (buffer[0] & 0xFF) << 24;
    size += (buffer[1] & 0xFF) << 16;
    size += (buffer[2] & 0xFF) << 8;
    size += (buffer[3] & 0xFF);

    return size;
}

void BufferReader::readBuffer(Context &context)
{
    int bufferSize = readSize(context.getClientSD());
    Buffer &buffer = context.getBuffer();
    /* prepare for size bufferSize + 1 for the final null to terminate the string, this will not be needed for compressed data */
    buffer.prepare((size_t) bufferSize + 1);
    readFully(context.getClientSD(), buffer.getBytes(), bufferSize);
    buffer.getBytes()[bufferSize] = '\0';
}
