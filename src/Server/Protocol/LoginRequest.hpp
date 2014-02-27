#ifndef _LOGINREQUEST_HPP_
#define _LOGINREQUEST_HPP_

class LoginRequest;

#include "Message.hpp"

class LoginRequest : public Message
{
public:
    std::string username;
    std::string password;

    LoginRequest();
    LoginRequest(const std::string &username, const std::string &password);
    virtual ~LoginRequest();

    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;

protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
