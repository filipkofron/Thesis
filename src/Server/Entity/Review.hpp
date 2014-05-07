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

#ifndef _REVIEW_HPP_
#define _REVIEW_HPP_

#include <string>

class Review;

#include "DAO/ReviewDAO.hpp"

class Review
{
private:
    int id;
    int userId;
    std::string addedOn;
    std::string reviewText;
    int foodId;
    float rating;
public:
    Review();
    Review(const int &id, const int &userId, const std::string &addedOn, const std::string &reviewText, const int &foodId, const float &rating);

    const int &getId() const;
    const int &getUserId() const;
    const std::string &getAddedOn() const;
    const std::string &getReviewText() const;
    const int &getFoodId() const;
    const float &getRating() const;

    void setUserId(const int &userId);
    void setAddedOn(const std::string &addedOn);
    void setReviewText(const std::string &reviewText);
    void setFoodId(const int &foodId);
    void setRating(const float &rating);

    static Review makeReview(const int &userId, const std::string &addedOn, const std::string &reviewText, const int &foodId, const float &rating, ReviewDAO &dao);
};

#endif
