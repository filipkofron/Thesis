#include "DeleteInventoryRequest.hpp"
#include "../Handler/DeleteInventoryHandler.hpp"

DeleteInventoryRequest::DeleteInventoryRequest()
{

}

DeleteInventoryRequest::~DeleteInventoryRequest()
{

}

std::string DeleteInventoryRequest::getStaticHeader()
{
    return "DeleteInventoryRequest";
}

std::string DeleteInventoryRequest::getHeader()
{
    return getStaticHeader();
}

Handler *DeleteInventoryRequest::createHandler()
{
    return new DeleteInventoryHandler(this);
}

void DeleteInventoryRequest::_dejsonize(Json::Value &content)
{
    id = content["id"].asInt();
}

void DeleteInventoryRequest::_jsonize(Json::Value &content)
{
    content["id"] = id;
}
