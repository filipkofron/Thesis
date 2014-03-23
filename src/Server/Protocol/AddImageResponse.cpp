#include "AddImageResponse.hpp"
#include "../Handler/NullHandler.hpp"

AddImageResponse::AddImageResponse()
{

}

AddImageResponse::AddImageResponse(const bool &success)
    : success(success)
{

}

AddImageResponse::~AddImageResponse()
{

}

std::string AddImageResponse::getStaticHeader()
{
    return "AddImageResponse";
}

std::string AddImageResponse::getHeader()
{
    return getStaticHeader();
}

Handler *AddImageResponse::createHandler()
{
    return new NullHandler();
}

void AddImageResponse::_dejsonize(Json::Value &content)
{
    success = content["success"].asBool();
}

void AddImageResponse::_jsonize(Json::Value &content)
{
    content["success"] = success;
}
