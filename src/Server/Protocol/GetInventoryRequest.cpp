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

#include "GetInventoryRequest.hpp"
#include "../Handler/GetInventoryHandler.hpp"

GetInventoryRequest::GetInventoryRequest()
{

}

GetInventoryRequest::~GetInventoryRequest()
{

}

std::string GetInventoryRequest::getStaticHeader()
{
    return "GetInventoryRequest";
}

std::string GetInventoryRequest::getHeader()
{
    return getStaticHeader();
}

Handler *GetInventoryRequest::createHandler()
{
    return new GetInventoryHandler(this);
}

void GetInventoryRequest::_dejsonize(Json::Value &content)
{
    direct = content["direct"].asBool();
    id = content["id"].asInt();
    food = content["food"].asString();
    gtin = content["gtin"].asString();
}

void GetInventoryRequest::_jsonize(Json::Value &content)
{
    content["direct"] = direct;
    content["id"] = id;
    content["food"] = food;
    content["gtin"] = gtin;
}
