#ifndef _GETFOODITEMHANDLER_HPP_
#define _GETFOODITEMHANDLER_HPP_

class GetFoodItemHandler;

#include "Handler.hpp"
#include "../Protocol/GetFoodItemRequest.hpp"

class GetFoodItemHandler : public Handler
{
private:
    GetFoodItemRequest *request;
public:
    GetFoodItemHandler(GetFoodItemRequest *request);
    virtual ~GetFoodItemHandler();
    virtual void handle(Context &context) override;
};

#endif
