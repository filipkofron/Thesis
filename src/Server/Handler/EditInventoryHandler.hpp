#ifndef _EDITINVENTORYHANDLER_HPP_
#define _EDITINVENTORYHANDLER_HPP_

class EditInventoryHandler;

#include "Handler.hpp"
#include "../Protocol/EditInventoryRequest.hpp"

class EditInventoryHandler : public Handler
{
private:
    EditInventoryRequest *request;
public:
    EditInventoryHandler(EditInventoryRequest *request);
    virtual ~EditInventoryHandler();
    virtual void handle(Context &context) override;
};

#endif
