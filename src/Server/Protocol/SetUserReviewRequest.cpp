/*
Copyright (C) 2014 Filip Kofron (filip.kofron.cz@gmail.com)

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

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
