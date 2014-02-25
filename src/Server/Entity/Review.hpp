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
