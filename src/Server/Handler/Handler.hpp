#ifndef _HANDLER_HPP_
#define _HANDLER_HPP_

class Handler;

#include "../Client/ClientContext.hpp"

class Handler
{
public:
    virtual ~Handler();
    virtual void handle(ClientContext &clientContext) = 0;
};


#endif
