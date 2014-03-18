#ifndef _DELETEINVENTORYRESPONSE_HPP_
#define _DELETEINVENTORYRESPONSE_HPP_

class DeleteInventoryResponse;

#include "Message.hpp"

class DeleteInventoryResponse : public Message
{
public:
    bool success;

    DeleteInventoryResponse();
    DeleteInventoryResponse(const bool &success);
    virtual ~DeleteInventoryResponse();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
