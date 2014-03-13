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
