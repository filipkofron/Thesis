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

#include "GetFoodBaseResponse.hpp"
#include "../Handler/NullHandler.hpp"

GetFoodBaseResponse::GetFoodBaseResponse()
{

}

GetFoodBaseResponse::GetFoodBaseResponse(const Json::Value &categories, const Json::Value &vendors)
    : categories(categories), vendors(vendors)
{

}

GetFoodBaseResponse::~GetFoodBaseResponse()
{

}

std::string GetFoodBaseResponse::getStaticHeader()
{
    return "GetFoodBaseResponse";
}

std::string GetFoodBaseResponse::getHeader()
{
    return getStaticHeader();
}

Handler *GetFoodBaseResponse::createHandler()
{
    return new NullHandler();
}

void GetFoodBaseResponse::_dejsonize(Json::Value &content)
{
    categories = content["categories"];
    vendors = content["vendors"];
}

void GetFoodBaseResponse::_jsonize(Json::Value &content)
{
    content["categories"] = categories;
    content["vendors"] = vendors;
}
