#ifndef _GETFOODITEMRESPONSE_HPP_
#define _GETFOODITEMRESPONSE_HPP_

class GetFoodItemResponse;

#include "Message.hpp"

class GetFoodItemResponse : public Message
{
public:
    Json::Value items;
    GetFoodItemResponse();
    GetFoodItemResponse(const Json::Value &items);
    virtual ~GetFoodItemResponse();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
