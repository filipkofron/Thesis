#ifndef _GETFOODBASEHANDLER_HPP_
#define _GETFOODBASEHANDLER_HPP_

class GetFoodBaseHandler;

#include "Handler.hpp"
#include "../Protocol/GetFoodBaseRequest.hpp"

class GetFoodBaseHandler : public Handler
{
private:
    GetFoodBaseRequest *request;
public:
    GetFoodBaseHandler(GetFoodBaseRequest *request);
    virtual ~GetFoodBaseHandler();
    virtual void handle(Context &context) override;
};

#endif
