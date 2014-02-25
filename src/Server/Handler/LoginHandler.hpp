#ifndef _LOGINHANDLER_HPP_
#define _LOGINHANDLER_HPP_

class LoginHandler;

#include "Handler.hpp"
#include "../Protocol/LoginRequest.hpp"

class LoginHandler : public Handler
{
private:
    LoginRequest *request;
public:
    LoginHandler(LoginRequest *request);
    virtual ~LoginHandler();
    virtual void handle(Context &context) override;
};

#endif
