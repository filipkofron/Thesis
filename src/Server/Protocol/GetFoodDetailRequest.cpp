#include "GetFoodDetailRequest.hpp"
#include "../Handler/GetFoodDetailHandler.hpp"

GetFoodDetailRequest::GetFoodDetailRequest()
{

}

GetFoodDetailRequest::~GetFoodDetailRequest()
{

}

std::string GetFoodDetailRequest::getStaticHeader()
{
    return "GetFoodDetailRequest";
}

std::string GetFoodDetailRequest::getHeader()
{
    return getStaticHeader();
}

Handler *GetFoodDetailRequest::createHandler()
{
    return new GetFoodDetailHandler(this);
}

void GetFoodDetailRequest::_dejsonize(Json::Value &content)
{
    id = content["id"].asInt();
}

void GetFoodDetailRequest::_jsonize(Json::Value &content)
{
    content["id"] = id;
}
