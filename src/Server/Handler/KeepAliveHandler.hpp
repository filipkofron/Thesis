#ifndef _KEEPALIVEHANDLER_HPP_
#define _KEEPALIVEHANDLER_HPP_

class KeepAliveHandler;

#include "Handler.hpp"
#include "../Protocol/KeepAlive.hpp"

class KeepAliveHandler : public Handler
{
private:
    KeepAlive *request;
public:
    KeepAliveHandler(KeepAlive *request);
    virtual ~KeepAliveHandler();
    virtual void handle(Context &context) override;
};

#endif
