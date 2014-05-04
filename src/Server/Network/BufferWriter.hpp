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

class BufferWriter
{
private:
    static void writeSize(const int &sd, const int &size);
    static void writeFully(const int &sd, const char *buffer, const int &len);
public:
    static void writeBuffer(Context &context);
};

#endif
