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
        Review::makeReview(context.getUserId(), Date::unixTimeToMysqlString(Date::currentTimeMilis()), request->text, food.getId(), request->rating, *reviewDao);
        Edit::makeEdit(context.getUserId(), food.getId(), Date::unixTimeToMysqlString(Date::currentTimeMilis()), "User added review.", *editDao);
    }
    else
    {
        if(deleted)
        {
            Edit::makeEdit(context.getUserId(), food.getId(), Date::unixTimeToMysqlString(Date::currentTimeMilis()), "User deleted review.", *editDao);
        }
    }

    SetUserReviewResponse response(true);
    MessageSender::sendMessage(context, response);
}
