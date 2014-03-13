#include "GetInventoryResponse.hpp"
#include "../Handler/NullHandler.hpp"

GetInventoryResponse::GetInventoryResponse()
{

}

GetInventoryResponse::GetInventoryResponse(const Json::Value &items)
    : items(items)
{

}

GetInventoryResponse::~GetInventoryResponse()
{

}

std::string GetInventoryResponse::getStaticHeader()
{
    return "GetInventoryResponse";
}

std::string GetInventoryResponse::getHeader()
{
    return getStaticHeader();
}

Handler *GetInventoryResponse::createHandler()
{
    return new NullHandler();
}

void GetInventoryResponse::_dejsonize(Json::Value &content)
{
    items = content["items"];
}

void GetInventoryResponse::_jsonize(Json::Value &content)
{
    content["items"] = items;
}
