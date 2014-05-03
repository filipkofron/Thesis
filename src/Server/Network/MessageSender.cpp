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

#include "MessageSender.hpp"
#include <jsoncpp/json/writer.h>
#include <jsoncpp/json/value.h>
#include "BufferWriter.hpp"
#include <cstring>

void MessageSender::sendMessage(Context &context, Message &message)
{
    Json::FastWriter writer;
    Json::Value root;

    message.jsonize(root);
    std::string str = writer.write(root);

    int size = str.length() + 1;

    context.getBuffer().prepare(size);
    memcpy(context.getBuffer().getBytes(), str.c_str(), size);

    BufferWriter::writeBuffer(context);
}
