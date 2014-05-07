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

#include "EditFoodRequest.hpp"
#include "../Handler/EditFoodHandler.hpp"

EditFoodRequest::EditFoodRequest()
{
}

EditFoodRequest::~EditFoodRequest()
{
}

std::string EditFoodRequest::getStaticHeader()
{
    return "EditFoodRequest";
}

std::string EditFoodRequest::getHeader()
{
    return getStaticHeader();
}

Handler *EditFoodRequest::createHandler()
{
    return new EditFoodHandler(this);
}

void EditFoodRequest::_dejsonize(Json::Value &content)
{
    adding = content["adding"].asBool();
    id = content["id"].asInt();
    categoryId = content["categoryId"].asInt();
    name = content["name"].asString();
    vendor = content["vendor"].asString();
    gtin = content["gtin"].asString();
    description = content["description"].asString();
    defaultUseBy = (int64_t) content["defaultUseBy"].asInt64();
    amountType = content["amountType"].asInt();
    amount = content["amount"].asFloat();
    usualPrice = content["usualPrice"].asFloat();
}

void EditFoodRequest::_jsonize(Json::Value &content)
{
    content["adding"] = adding;
    content["id"] = id;
    content["name"] = name;
    content["vendor"] = vendor;
    content["categoryId"] = categoryId;
    content["gtin"]= gtin;
    content["description"] = description;
    content["defaultUseBy"] = (Json::Int64) defaultUseBy;
    content["amountType"] = amountType;
    content["amount"] = amount;
    content["usualPrice"] = usualPrice;
}
