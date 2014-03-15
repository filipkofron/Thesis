#ifndef _GETFOODDETAILHANDLER_HPP_
#define _GETFOODDETAILHANDLER_HPP_

class GetFoodDetailHandler;

#include "Handler.hpp"
#include "../Protocol/GetFoodDetailRequest.hpp"

class GetFoodDetailHandler : public Handler
{
private:
    GetFoodDetailRequest *request;
public:
    GetFoodDetailHandler(GetFoodDetailRequest *request);
    virtual ~GetFoodDetailHandler();
    virtual void handle(Context &context) override;
};

#endif
