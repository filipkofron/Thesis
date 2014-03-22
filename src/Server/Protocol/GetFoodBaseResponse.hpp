#ifndef _GETFOODBASERESPONSE_HPP_
#define _GETFOODBASERESPONSE_HPP_

class GetFoodBaseResponse;

#include "Message.hpp"

class GetFoodBaseResponse : public Message
{
public:
    Json::Value categories;
    Json::Value vendors;
    GetFoodBaseResponse();
    GetFoodBaseResponse(const Json::Value &categories, const Json::Value &vendors);
    virtual ~GetFoodBaseResponse();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
