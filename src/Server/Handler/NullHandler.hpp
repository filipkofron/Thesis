#ifndef _NULLHANDLER_HPP_
#define _NULLHANDLER_HPP_

class NullHandler;

#include "Handler.hpp"

class NullHandler : public Handler
{
public:
    virtual ~NullHandler();
    virtual void handle(Context &contex) override;
};

#endif
