#ifndef _SETUSERRATINGHANDLER_HPP_
#define _SETUSERRATINGHANDLER_HPP_

class SetUserRatingHandler;

#include "Handler.hpp"
#include "../Protocol/SetUserReviewRequest.hpp"

class SetUserRatingHandler : public Handler
{
private:
    SetUserReviewRequest *request;
public:
    SetUserRatingHandler(SetUserReviewRequest *request);
    virtual ~SetUserRatingHandler();
    virtual void handle(Context &context) override;
};

#endif
