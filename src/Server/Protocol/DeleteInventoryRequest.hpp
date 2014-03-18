#ifndef _DELETEINVENTORYREQUEST_HPP_
#define _DELETEINVENTORYREQUEST_HPP_

class DeleteInventoryRequest;

#include "Message.hpp"

class DeleteInventoryRequest : public Message
{
public:
    int id;

    DeleteInventoryRequest();
    virtual ~DeleteInventoryRequest();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
