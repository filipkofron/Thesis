#include "GetFoodBaseResponse.hpp"
#include "../Handler/NullHandler.hpp"

GetFoodBaseResponse::GetFoodBaseResponse()
{

}

GetFoodBaseResponse::GetFoodBaseResponse(const Json::Value &categories, const Json::Value &vendors)
    : categories(categories), vendors(vendors)
{

}

GetFoodBaseResponse::~GetFoodBaseResponse()
{

}

std::string GetFoodBaseResponse::getStaticHeader()
{
    return "GetFoodBaseResponse";
}

std::string GetFoodBaseResponse::getHeader()
{
    return getStaticHeader();
}

Handler *GetFoodBaseResponse::createHandler()
{
    return new NullHandler();
}

void GetFoodBaseResponse::_dejsonize(Json::Value &content)
{
    categories = content["categories"];
    vendors = content["vendors"];
}

void GetFoodBaseResponse::_jsonize(Json::Value &content)
{
    content["categories"] = categories;
    content["vendors"] = vendors;
}
