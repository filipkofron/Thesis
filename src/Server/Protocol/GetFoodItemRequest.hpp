#ifndef _GETFOODITEMREQUEST_HPP_
#define _GETFOODITEMREQUEST_HPP_

class GetFoodItemRequest;

#include "Message.hpp"

class GetFoodItemRequest : public Message
{
public:
    bool direct;
    int id;
    std::string name;
    std::string gtin;
    int skip;
    GetFoodItemRequest();
    virtual ~GetFoodItemRequest();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
