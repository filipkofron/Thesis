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
