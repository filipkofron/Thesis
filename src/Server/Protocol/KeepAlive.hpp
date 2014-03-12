#ifndef _KEEPALIVE_HPP_
#define _KEEPALIVE_HPP_

class KeepAlive;

#include "Message.hpp"

class KeepAlive : public Message
{
public:
    KeepAlive();
    virtual ~KeepAlive();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
