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

#include "GetFoodItemResponse.hpp"
#include "../Handler/NullHandler.hpp"

GetFoodItemResponse::GetFoodItemResponse()
{

}

GetFoodItemResponse::GetFoodItemResponse(const Json::Value &items)
    : items(items)
{

}

GetFoodItemResponse::~GetFoodItemResponse()
{

}

std::string GetFoodItemResponse::getStaticHeader()
{
    return "GetFoodItemResponse";
}

std::string GetFoodItemResponse::getHeader()
{
    return getStaticHeader();
}

Handler *GetFoodItemResponse::createHandler()
{
    return new NullHandler();
}

void GetFoodItemResponse::_dejsonize(Json::Value &content)
{
    items = content["foods"];
}

void GetFoodItemResponse::_jsonize(Json::Value &content)
{
    content["foods"] = items;
}
