#include "AddImageRequest.hpp"
#include "../Handler/AddImageHandler.hpp"

AddImageRequest::AddImageRequest()
{

}

AddImageRequest::~AddImageRequest()
{

}

std::string AddImageRequest::getStaticHeader()
{
    return "AddImageRequest";
}

std::string AddImageRequest::getHeader()
{
    return getStaticHeader();
}

Handler *AddImageRequest::createHandler()
{
    return new AddImageHandler(this);
}

void AddImageRequest::_dejsonize(Json::Value &content)
{
    foodId = content["foodId"].asInt();
    image = content["image"].asString();
}

void AddImageRequest::_jsonize(Json::Value &content)
{
    content["foodId"] = foodId;
    content["image"] = image;
}
