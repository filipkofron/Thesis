#ifndef _GETFOODDETAILREQUEST_HPP_
#define _GETFOODDETAILREQUEST_HPP_

class GetFoodDetailRequest;

#include "Message.hpp"

class GetFoodDetailRequest : public Message
{
public:
    int id;
    GetFoodDetailRequest();
    virtual ~GetFoodDetailRequest();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
