#include "GetFoodBaseRequest.hpp"
#include "../Handler/GetFoodBaseHandler.hpp"

GetFoodBaseRequest::GetFoodBaseRequest()
{

}

GetFoodBaseRequest::~GetFoodBaseRequest()
{

}

std::string GetFoodBaseRequest::getStaticHeader()
{
    return "GetFoodBaseRequest";
}

std::string GetFoodBaseRequest::getHeader()
{
    return getStaticHeader();
}

Handler *GetFoodBaseRequest::createHandler()
{
    return new GetFoodBaseHandler(this);
}

void GetFoodBaseRequest::_dejsonize(Json::Value &content)
{
}

void GetFoodBaseRequest::_jsonize(Json::Value &content)
{
}
