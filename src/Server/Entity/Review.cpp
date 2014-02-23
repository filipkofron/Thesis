#include "Review.hpp"

Review::Review()
{

}

Review::Review(const int &id, const int &userId, const std::string &addedOn, const std::string &reviewText, const int &foodId, const float &rating)
    : id(id), userId(userId), addedOn(addedOn), reviewText(reviewText), foodId(foodId), rating(rating)
{

}

const int &Review::getId() const
{
    return id;
}

const int &Review::getUserId() const
{
    return userId;
}

const std::string &Review::getAddedOn() const
{
    return addedOn;
}

const std::string &Review::getReviewText() const
{
    return reviewText;
}

const int &Review::getFoodId() const
{
    return foodId;
}

const float &Review::getRating() const
{
    return rating;
}

void Review::setUserId(const int &userId)
{
    this->userId = userId;
}

void Review::setAddedOn(const std::string &addedOn)
{
    this->addedOn = addedOn;
}

void Review::setReviewText(const std::string &reviewText)
{
    this->reviewText = reviewText;
}

void Review::setFoodId(const int &foodId)
{
    this->foodId = foodId;
}

void Review::setRating(const float &rating)
{
    this->rating = rating;
}
