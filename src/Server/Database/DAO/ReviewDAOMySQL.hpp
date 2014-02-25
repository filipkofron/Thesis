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
