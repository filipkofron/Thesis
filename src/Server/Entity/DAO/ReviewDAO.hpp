#ifndef _REVIEWDAO_HPP_
#define _REVIEWDAO_HPP_

class ReviewDAO;

#include "../Review.hpp"

#include <string>
#include <vector>

class ReviewDAO
{
public:
    ReviewDAO() { }
    virtual ~ReviewDAO() { }

    virtual void addReview(Review &review, int &newId) = 0;
    virtual void deleteReview(Review &review) = 0;
    virtual void updateReview(const Review &review) = 0;
    virtual Review getReviewById(const int &id) = 0;
    virtual std::vector<Review> getReviewByUserId(const int &userId) = 0;
    virtual std::vector<Review> getReviewByFoodId(const int &foodId) = 0;
};

#endif
