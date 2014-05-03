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

#ifndef _REVIEWDAOMYSQL_HPP_
#define _REVIEWDAOMYSQL_HPP_

class ReviewDAOMySQL;

#include "../../Entity/DAO/ReviewDAO.hpp"

class ReviewDAOMySQL : public ReviewDAO
{
public:
    ReviewDAOMySQL() { }
    virtual ~ReviewDAOMySQL() { }

    virtual void addReview(Review &review, int &newId);
    virtual void deleteReview(Review &review);
    virtual void updateReview(const Review &review);
    virtual Review getReviewById(const int &id);
    virtual std::vector<Review> getReviewByUserId(const int &userId);
    virtual std::vector<Review> getReviewByFoodId(const int &foodId);
};

#endif
