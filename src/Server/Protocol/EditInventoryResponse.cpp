#include "EditInventoryResponse.hpp"
#include "../Handler/NullHandler.hpp"

EditInventoryResponse::EditInventoryResponse()
{

}

EditInventoryResponse::EditInventoryResponse(const bool &success)
    : success(success)
{

}

EditInventoryResponse::~EditInventoryResponse()
{

}

std::string EditInventoryResponse::getStaticHeader()
{
    return "EditInventoryResponse";
}

std::string EditInventoryResponse::getHeader()
{
    return getStaticHeader();
}

Handler *EditInventoryResponse::createHandler()
{
    return new NullHandler();
}

void EditInventoryResponse::_dejsonize(Json::Value &content)
{
    success = content["success"].asBool();
}

void EditInventoryResponse::_jsonize(Json::Value &content)
{
    content["success"] = success;
}
