#include "GetFoodItemResponse.hpp"
#include "../Handler/NullHandler.hpp"

GetFoodItemResponse::GetFoodItemResponse()
{

}

GetFoodItemResponse::GetFoodItemResponse(const Json::Value &items)
    : items(items)
{

}

GetFoodItemResponse::~GetFoodItemResponse()
{

}

std::string GetFoodItemResponse::getStaticHeader()
{
    return "GetFoodItemResponse";
}

std::string GetFoodItemResponse::getHeader()
{
    return getStaticHeader();
}

Handler *GetFoodItemResponse::createHandler()
{
    return new NullHandler();
}

void GetFoodItemResponse::_dejsonize(Json::Value &content)
{
    items = content["foods"];
}

void GetFoodItemResponse::_jsonize(Json::Value &content)
{
    content["foods"] = items;
}
