#ifndef _EDITFOODHANDLER_HPP_
#define _EDITFOODHANDLER_HPP_

class EditFoodHandler;

#include "Handler.hpp"
#include "../Protocol/EditFoodRequest.hpp"

class EditFoodHandler : public Handler
{
private:
    EditFoodRequest *request;
public:
    EditFoodHandler(EditFoodRequest *request);
    virtual ~EditFoodHandler();
    virtual void handle(Context &context) override;
};

#endif
