#ifndef _LOGINRESPONSE_HPP_
#define _LOGINRESPONSE_HPP_

class LoginResponse;

#include "Message.hpp"

class LoginResponse : public Message
{
public:
    bool success;
    std::string message;
    LoginResponse();
    LoginResponse(const bool &success, const std::string &message);
    virtual ~LoginResponse();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(const Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
