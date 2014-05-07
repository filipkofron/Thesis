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

#ifndef _MESSAGE_HPP_
#define _MESSAGE_HPP_

class Message;

#include <jsoncpp/json/reader.h>
#include <jsoncpp/json/value.h>
#include "../Handler/Handler.hpp"

class Message
{
protected:
    Message();
    virtual void _dejsonize(Json::Value &content) = 0;
    virtual void _jsonize(Json::Value &content) = 0;

public:
    virtual std::string getHeader() = 0;
    virtual ~Message();

    static Message *dejsonize(Json::Value &root);
    virtual void jsonize(Json::Value &root) final;
    virtual Handler *createHandler() = 0;
};

#endif
