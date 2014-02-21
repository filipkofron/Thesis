#include "LoginRequest.hpp"
#include "../Handler/LoginHandler.hpp"

LoginRequest::LoginRequest()
{

}

LoginRequest::LoginRequest(const std::string &username, const std::string &password)
    : username(username), password(password)
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

void LoginRequest::_dejsonize(const Json::Value &content)
{
    username = content["username"].asString();
    password = content["password"].asString();
}

void LoginRequest::_jsonize(Json::Value &content)
{
    content["username"] = username;
    content["password"] = password;
}
