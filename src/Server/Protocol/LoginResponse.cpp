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

#include "LoginResponse.hpp"
#include "../Handler/NullHandler.hpp"

std::string LoginResponse::getStaticHeader()
{
    return "LoginResponse";
}

std::string LoginResponse::getHeader()
{
    return getStaticHeader();
}

LoginResponse::LoginResponse()
{

}

LoginResponse::LoginResponse(const bool &success, const std::string &message)
    : success(success), message(message)
{

}

LoginResponse::~LoginResponse()
{

}

Handler *LoginResponse::createHandler()
{
    return new NullHandler();
}

void LoginResponse::_dejsonize(Json::Value &content)
{
    success = content["success"].asBool();
    message = content["message"].asString();
}

void LoginResponse::_jsonize(Json::Value &content)
{
    content["success"] = success;
    content["message"] = message;
}
