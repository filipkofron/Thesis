#include "LoginRequest.hpp"
#include "../Handler/LoginHandler.hpp"

LoginRequest::LoginRequest()
{

}

LoginRequest::LoginRequest(const std::string &username)
    : username(username)
{

}

LoginRequest::~LoginRequest()
{

}

std::string LoginRequest::getStaticHeader()
{
    return "LoginRequest";
}

std::string LoginRequest::getHeader()
{
    return getStaticHeader();
}

Handler *LoginRequest::createHandler()
{
    return new LoginHandler(this);
}

void LoginRequest::_dejsonize(Json::Value &content)
{
    username = content["username"].asString();
}

void LoginRequest::_jsonize(Json::Value &content)
{
    content["username"] = username;
}
