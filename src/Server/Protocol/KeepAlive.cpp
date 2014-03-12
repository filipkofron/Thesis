#include "KeepAlive.hpp"
#include "../Handler/NullHandler.hpp"

KeepAlive::KeepAlive()
{

}

KeepAlive::~KeepAlive()
{

}

std::string KeepAlive::getStaticHeader()
{
    return "KeepAlive";
}

std::string KeepAlive::getHeader()
{
    return getStaticHeader();
}

Handler *KeepAlive::createHandler()
{
    return new NullHandler();
}

void KeepAlive::_dejsonize(Json::Value &content)
{
}

void KeepAlive::_jsonize(Json::Value &content)
{
}
