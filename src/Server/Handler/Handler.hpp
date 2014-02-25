#ifndef _HANDLER_HPP_
#define _HANDLER_HPP_

class Handler;

#include "../Client/Context.hpp"

class Handler
{
public:
    virtual ~Handler();
    virtual void handle(Context &context) = 0;
};


#endif
