#include "LoginResponse.hpp"
#include "../Handler/NullHandler.hpp"

std::string LoginResponse::getStaticHeader()
{
    return "LoginResponse";
}

std::string LoginResponse::getHeader()
{
    return getStaticHeader();
}

LoginResponse::LoginResponse()
{

}

LoginResponse::LoginResponse(const bool &success, const std::string &message)
    : success(success), message(message)
{

}

LoginResponse::~LoginResponse()
{

}

Handler *LoginResponse::createHandler()
{
    return new NullHandler();
}

void LoginResponse::_dejsonize(const Json::Value &content)
{
    success = content["success"].asBool();
    message = content["message"].asString();
}

void LoginResponse::_jsonize(Json::Value &content)
{
    content["success"] = success;
    content["message"] = message;
}
