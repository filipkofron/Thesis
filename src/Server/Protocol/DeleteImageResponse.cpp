#include "DeleteImageResponse.hpp"
#include "../Handler/NullHandler.hpp"

DeleteImageResponse::DeleteImageResponse()
{

}

DeleteImageResponse::DeleteImageResponse(const bool &success)
    : success(success)
{

}

DeleteImageResponse::~DeleteImageResponse()
{

}

std::string DeleteImageResponse::getStaticHeader()
{
    return "DeleteImageResponse";
}

std::string DeleteImageResponse::getHeader()
{
    return getStaticHeader();
}

Handler *DeleteImageResponse::createHandler()
{
    return new NullHandler();
}

void DeleteImageResponse::_dejsonize(Json::Value &content)
{
    success = content["success"].asBool();
}

void DeleteImageResponse::_jsonize(Json::Value &content)
{
    content["success"] = success;
}
