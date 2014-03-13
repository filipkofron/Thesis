#ifndef _HANDLEREXCEPTION_HPP_
#define _HANDLEREXCEPTION_HPP_

class HandlerException;

#include <string>

class HandlerException
{
public:
    std::string message;
    HandlerException(const std::string &message);
};


#endif
