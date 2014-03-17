#include "EditInventoryRequest.hpp"
#include "../Handler/EditInventoryHandler.hpp"
#include "../Util/Date.hpp"

EditInventoryRequest::EditInventoryRequest()
{

}

EditInventoryRequest::~EditInventoryRequest()
{
}

std::string EditInventoryRequest::getStaticHeader()
{
    return "EditInventoryRequest";
}

std::string EditInventoryRequest::getHeader()
{
    return getStaticHeader();
}

Handler *EditInventoryRequest::createHandler()
{
    return new EditInventoryHandler(this);
}

void EditInventoryRequest::_dejsonize(Json::Value &content)
{
    adding = content["adding"].asBool();
    id = content["id"].asInt();
    foodId = content["foodId"].asInt();
    useBy = Date::unixTimeFromMysqlString(content["useBy"].asString());
    count = content["count"].asInt();
}

void EditInventoryRequest::_jsonize(Json::Value &content)
{
    content["adding"] = adding;
    content["id"] = id;
    content["foodId"] = foodId;
    content["useBy"] = Date::unixTimeToMysqlString(useBy);
    content["count"] = count;
}
