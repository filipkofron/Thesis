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

#include "EditInventoryRequest.hpp"
#include "../Handler/EditInventoryHandler.hpp"
#include "../Util/Date.hpp"

EditInventoryRequest::EditInventoryRequest()
{

}

EditInventoryRequest::~EditInventoryRequest()
{
}

std::string EditInventoryRequest::getStaticHeader()
{
    return "EditInventoryRequest";
}

std::string EditInventoryRequest::getHeader()
{
    return getStaticHeader();
}

Handler *EditInventoryRequest::createHandler()
{
    return new EditInventoryHandler(this);
}

void EditInventoryRequest::_dejsonize(Json::Value &content)
{
    adding = content["adding"].asBool();
    id = content["id"].asInt();
    foodId = content["foodId"].asInt();
    useBy = Date::unixTimeFromMysqlString(content["useBy"].asString());
    count = content["count"].asInt();
}

void EditInventoryRequest::_jsonize(Json::Value &content)
{
    content["adding"] = adding;
    content["id"] = id;
    content["foodId"] = foodId;
    content["useBy"] = Date::unixTimeToMysqlString(useBy);
    content["count"] = count;
}
