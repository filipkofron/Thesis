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

#include "SetUserRatingHandler.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Protocol/SetUserReviewResponse.hpp"
#include "../Network/MessageSender.hpp"
#include "../Util/Date.hpp"

SetUserRatingHandler::SetUserRatingHandler(SetUserReviewRequest *request)
    : request(request)
{

}

SetUserRatingHandler::~SetUserRatingHandler()
{

}

void SetUserRatingHandler::handle(Context &context)
{
    if(!context.getLoggedIn())
    {
        SetUserReviewResponse response(false);
        MessageSender::sendMessage(context, response);
    }

    std::shared_ptr<FoodDAO> foodDao = DAOFactory::getFoodDAO();
    std::shared_ptr<ReviewDAO> reviewDao = DAOFactory::getReviewDAO();
    std::shared_ptr<EditDAO> editDao = DAOFactory::getEditDAO();

    Food food = foodDao->getFoodById(request->foodId);

    std::vector<Review> reviews = reviewDao->getReviewByFoodId(food.getId());

    bool deleted = false;
    for(Review &review : reviews)
    {
        if(review.getUserId() == context.getUserId())
        {
            deleted = true;
            reviewDao->deleteReview(review);
        }
    }

    if(!request->deleteReview)
    {
        Review::makeReview(context.getUserId(), Date::unixTimeToMysqlString(Date::currentTimeMilis() / 1000), request->text, food.getId(), request->rating, *reviewDao);
        Edit::makeEdit(context.getUserId(), food.getId(), Date::unixTimeToMysqlString(Date::currentTimeMilis() / 1000), "User added review.", *editDao);
    }
    else
    {
        if(deleted)
        {
            Edit::makeEdit(context.getUserId(), food.getId(), Date::unixTimeToMysqlString(Date::currentTimeMilis() / 1000), "User deleted review.", *editDao);
        }
    }

    SetUserReviewResponse response(true);
    MessageSender::sendMessage(context, response);
}
