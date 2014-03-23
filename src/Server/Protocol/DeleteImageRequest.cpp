#include "DeleteImageRequest.hpp"
#include "../Handler/DeleteImageHandler.hpp"

DeleteImageRequest::DeleteImageRequest()
{

}

DeleteImageRequest::~DeleteImageRequest()
{

}

std::string DeleteImageRequest::getStaticHeader()
{
    return "DeleteImageRequest";
}

std::string DeleteImageRequest::getHeader()
{
    return getStaticHeader();
}

Handler *DeleteImageRequest::createHandler()
{
    return new DeleteImageHandler(this);
}

void DeleteImageRequest::_dejsonize(Json::Value &content)
{
    id = content["id"].asInt();
}

void DeleteImageRequest::_jsonize(Json::Value &content)
{
    content["id"] = id;
}
