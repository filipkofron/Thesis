#ifndef _DELETEINVENTORYHANDLER_HPP_
#define _DELETEINVENTORYHANDLER_HPP_

class DeleteInventoryHandler;

#include "Handler.hpp"
#include "../Protocol/DeleteInventoryRequest.hpp"

class DeleteInventoryHandler : public Handler
{
private:
    DeleteInventoryRequest *request;
public:
    DeleteInventoryHandler(DeleteInventoryRequest *request);
    virtual ~DeleteInventoryHandler();
    virtual void handle(Context &context) override;
};

#endif
