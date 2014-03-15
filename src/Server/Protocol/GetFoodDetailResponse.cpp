#include "GetFoodDetailResponse.hpp"
#include "../Handler/NullHandler.hpp"

GetFoodDetailResponse::GetFoodDetailResponse()
{

}

GetFoodDetailResponse::GetFoodDetailResponse(const Json::Value &foodDetails)
    : foodDetails(foodDetails)
{

}

GetFoodDetailResponse::~GetFoodDetailResponse()
{

}

std::string GetFoodDetailResponse::getStaticHeader()
{
    return "GetFoodDetailResponse";
}

std::string GetFoodDetailResponse::getHeader()
{
    return getStaticHeader();
}

Handler *GetFoodDetailResponse::createHandler()
{
    return new NullHandler();
}

void GetFoodDetailResponse::_dejsonize(Json::Value &content)
{
    foodDetails = content["foodDetails"];
}

void GetFoodDetailResponse::_jsonize(Json::Value &content)
{
    content["foodDetails"] = foodDetails;
}
