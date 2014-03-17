#ifndef _EDITINVENTORYREQUEST_HPP_
#define _EDITINVENTORYREQUEST_HPP_

class EditInventoryRequest;

#include <inttypes.h>
#include "Message.hpp"

class EditInventoryRequest : public Message
{
public:
    bool adding;
    int id;
    int foodId;
    int64_t useBy;
    int count;

    EditInventoryRequest();
    virtual ~EditInventoryRequest();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
