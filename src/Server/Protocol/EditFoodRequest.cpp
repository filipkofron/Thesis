#include "EditFoodRequest.hpp"
#include "../Handler/EditFoodHandler.hpp"

EditFoodRequest::EditFoodRequest()
{
}

EditFoodRequest::~EditFoodRequest()
{
}

std::string EditFoodRequest::getStaticHeader()
{
    return "EditFoodRequest";
}

std::string EditFoodRequest::getHeader()
{
    return getStaticHeader();
}

Handler *EditFoodRequest::createHandler()
{
    return new EditFoodHandler(this);
}

void EditFoodRequest::_dejsonize(Json::Value &content)
{
    adding = content["adding"].asBool();
    id = content["id"].asInt();
    categoryId = content["categoryId"].asInt();
    name = content["name"].asString();
    vendor = content["vendor"].asString();
    gtin = content["gtin"].asString();
    description = content["description"].asString();
    defaultUseBy = (int64_t) content["defaultUseBy"].asInt64();
    amountType = content["amountType"].asInt();
    amount = content["amount"].asFloat();
    usualPrice = content["usualPrice"].asFloat();
}

void EditFoodRequest::_jsonize(Json::Value &content)
{
    content["adding"] = adding;
    content["id"] = id;
    content["name"] = name;
    content["vendor"] = vendor;
    content["categoryId"] = categoryId;
    content["gtin"]= gtin;
    content["description"] = description;
    content["defaultUseBy"] = (Json::Int64) defaultUseBy;
    content["amountType"] = amountType;
    content["amount"] = amount;
    content["usualPrice"] = usualPrice;
}
