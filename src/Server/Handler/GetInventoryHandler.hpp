#ifndef _GETINVENTORYHANDLER_HPP_
#define _GETINVENTORYHANDLER_HPP_

class GetInventoryHandler;

#include "Handler.hpp"
#include "../Protocol/GetInventoryRequest.hpp"

class GetInventoryHandler : public Handler
{
private:
    GetInventoryRequest *request;
public:
    GetInventoryHandler(GetInventoryRequest *request);
    virtual ~GetInventoryHandler();
    virtual void handle(Context &context) override;
};

#endif
