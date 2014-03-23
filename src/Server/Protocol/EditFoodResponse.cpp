#include "EditFoodResponse.hpp"
#include "../Handler/NullHandler.hpp"

EditFoodResponse::EditFoodResponse()
{
}

EditFoodResponse::EditFoodResponse(const bool &success)
    : success (success)
{
}

EditFoodResponse::~EditFoodResponse()
{
}

std::string EditFoodResponse::getStaticHeader()
{
    return "EditFoodResponse";
}

std::string EditFoodResponse::getHeader()
{
    return getStaticHeader();
}

Handler *EditFoodResponse::createHandler()
{
    return new NullHandler();
}

void EditFoodResponse::_dejsonize(Json::Value &content)
{
    success = content["success"].asBool();
    id = content["id"].asInt();
}

void EditFoodResponse::_jsonize(Json::Value &content)
{
    content["success"] = success;
    content["id"] = id;
}
