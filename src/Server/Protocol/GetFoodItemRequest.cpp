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

#include "GetFoodItemRequest.hpp"
#include "../Handler/GetFoodItemHandler.hpp"

GetFoodItemRequest::GetFoodItemRequest()
{

}

GetFoodItemRequest::~GetFoodItemRequest()
{

}

std::string GetFoodItemRequest::getStaticHeader()
{
    return "GetFoodItemRequest";
}

std::string GetFoodItemRequest::getHeader()
{
    return getStaticHeader();
}

Handler *GetFoodItemRequest::createHandler()
{
    return new GetFoodItemHandler(this);
}

void GetFoodItemRequest::_dejsonize(Json::Value &content)
{
    direct = content["direct"].asBool();
    id = content["id"].asInt();
    name = content["name"].asString();
    gtin = content["gtin"].asString();
    skip = content["skip"].asInt();
}

void GetFoodItemRequest::_jsonize(Json::Value &content)
{
    content["direct"] = direct;
    content["id"] = id;
    content["name"] = name;
    content["gtin"] = gtin;
    content["skip"] = skip;
}
