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

Review Review::makeReview(const int &userId, const std::string &addedOn, const std::string &reviewText, const int &foodId, const float &rating, ReviewDAO &dao)
{
    Review review(0, userId, addedOn, reviewText, foodId, rating);

    int newId = 0;
    dao.addReview(review, newId);

    review.id = newId;

    return review;
}
