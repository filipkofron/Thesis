#ifndef _GETFOODDETAILRESPONSE_HPP_
#define _GETFOODDETAILRESPONSE_HPP_

class GetFoodDetailResponse;

#include "Message.hpp"

class GetFoodDetailResponse : public Message
{
public:
    Json::Value foodDetails;
    GetFoodDetailResponse();
    GetFoodDetailResponse(const Json::Value &foodDetails);
    virtual ~GetFoodDetailResponse();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
