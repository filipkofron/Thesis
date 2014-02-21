#ifndef _MESSAGE_HPP_
#define _MESSAGE_HPP_

class Message;

#include <jsoncpp/json/reader.h>
#include <jsoncpp/json/value.h>
#include "../Handler/Handler.hpp"

class Message
{
protected:
    Message();
    virtual void _dejsonize(const Json::Value &content) = 0;
    virtual void _jsonize(Json::Value &content) = 0;

public:
    virtual std::string getHeader() = 0;
    virtual ~Message();

    static Message *dejsonize(const Json::Value &root);
    virtual void jsonize(Json::Value &root) final;
    virtual Handler *createHandler() = 0;
};

#endif
