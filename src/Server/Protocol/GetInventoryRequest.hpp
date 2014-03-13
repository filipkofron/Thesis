#ifndef _GETINVENTORYREQUEST_HPP_
#define _GETINVENTORYREQUEST_HPP_

class GetInventoryRequest;

#include "Message.hpp"

class GetInventoryRequest : public Message
{
public:
    bool direct;
    int id;
    std::string food;
    std::string gtin;
    GetInventoryRequest();
    virtual ~GetInventoryRequest();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
