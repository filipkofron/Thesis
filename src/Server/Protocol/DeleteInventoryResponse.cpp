#include "DeleteInventoryResponse.hpp"
#include "../Handler/NullHandler.hpp"

DeleteInventoryResponse::DeleteInventoryResponse()
{

}

DeleteInventoryResponse::DeleteInventoryResponse(const bool &success)
    : success(success)
{

}

DeleteInventoryResponse::~DeleteInventoryResponse()
{

}

std::string DeleteInventoryResponse::getStaticHeader()
{
    return "DeleteInventoryResponse";
}

std::string DeleteInventoryResponse::getHeader()
{
    return getStaticHeader();
}

Handler *DeleteInventoryResponse::createHandler()
{
    return new NullHandler();
}

void DeleteInventoryResponse::_dejsonize(Json::Value &content)
{
    success = content["success"].asBool();
}

void DeleteInventoryResponse::_jsonize(Json::Value &content)
{
    content["success"] = success;
}
