#include "SetUserReviewResponse.hpp"
#include "../Handler/NullHandler.hpp"

SetUserReviewResponse::SetUserReviewResponse()
{

}

SetUserReviewResponse::SetUserReviewResponse(const bool &success)
    : success(success)
{

}

SetUserReviewResponse::~SetUserReviewResponse()
{

}

std::string SetUserReviewResponse::getStaticHeader()
{
    return "SetUserReviewResponse";
}

std::string SetUserReviewResponse::getHeader()
{
    return getStaticHeader();
}

Handler *SetUserReviewResponse::createHandler()
{
    return new NullHandler();
}

void SetUserReviewResponse::_dejsonize(Json::Value &content)
{
    success = content["success"].asBool();
}

void SetUserReviewResponse::_jsonize(Json::Value &content)
{
    content["success"] = success;
}
