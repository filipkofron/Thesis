#ifndef _GETFOODBASEREQUEST_HPP_
#define _GETFOODBASEREQUEST_HPP_

class GetFoodBaseRequest;

#include "Message.hpp"

class GetFoodBaseRequest : public Message
{
public:
    GetFoodBaseRequest();
    virtual ~GetFoodBaseRequest();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
