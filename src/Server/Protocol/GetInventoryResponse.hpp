#ifndef _GETINVENTORYRESPONSE_HPP_
#define _GETINVENTORYRESPONSE_HPP_

class GetInventoryResponse;

#include "Message.hpp"

class GetInventoryResponse : public Message
{
public:
    Json::Value items;
    GetInventoryResponse();
    GetInventoryResponse(const Json::Value &items);
    virtual ~GetInventoryResponse();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
