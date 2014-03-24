#ifndef _SETUSERREVIEWRESPONSE_HPP_
#define _SETUSERREVIEWRESPONSE_HPP_

class SetUserReviewResponse;

#include "Message.hpp"

class SetUserReviewResponse : public Message
{
public:
    bool success;
    SetUserReviewResponse();
    SetUserReviewResponse(const bool &success);
    virtual ~SetUserReviewResponse();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
