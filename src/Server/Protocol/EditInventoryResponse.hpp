#ifndef _EDITINVENTORYRESPONSE_HPP_
#define _EDITINVENTORYRESPONSE_HPP_

class EditInventoryResponse;

#include "Message.hpp"

class EditInventoryResponse : public Message
{
public:
    bool success;
    EditInventoryResponse();
    EditInventoryResponse(const bool &success);
    virtual ~EditInventoryResponse();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
