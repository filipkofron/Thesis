#ifndef _SETUSERREVIEWREQUEST_HPP_
#define _SETUSERREVIEWREQUEST_HPP_

class SetUserReviewRequest;

#include "Message.hpp"

class SetUserReviewRequest : public Message
{
public:
    float rating;
    bool deleteReview;
    int foodId;
    std::string text;

    SetUserReviewRequest();
    virtual ~SetUserReviewRequest();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
