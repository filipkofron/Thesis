#include "SetUserReviewRequest.hpp"
#include "../Handler/SetUserRatingHandler.hpp"

SetUserReviewRequest::SetUserReviewRequest()
{

}

SetUserReviewRequest::~SetUserReviewRequest()
{

}

std::string SetUserReviewRequest::getStaticHeader()
{
    return "SetUserReviewRequest";
}

std::string SetUserReviewRequest::getHeader()
{
    return getStaticHeader();
}

Handler *SetUserReviewRequest::createHandler()
{
    return new SetUserRatingHandler(this);
}

void SetUserReviewRequest::_dejsonize(Json::Value &content)
{
    rating = content["rating"].asFloat();
    deleteReview = content["delete"].asBool();
    foodId = content["foodId"].asInt();
    text = content["text"].asString();
}

void SetUserReviewRequest::_jsonize(Json::Value &content)
{
    content["rating"] = rating;
    content["delete"] = deleteReview;
    content["foodId"] = foodId;
    content["text"] = text;
}
