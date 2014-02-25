#ifndef _CLIENTCONTEXT_HPP_
#define _CLIENTCONTEXT_HPP_

class Context;

#include "Buffer.hpp"

class Context
{
private:
    Buffer buffer;
public:
    Buffer &getBuffer();
};

#endif
