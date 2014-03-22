#ifndef _EDITFOODREQUEST_HPP_
#define _EDITFOODREQUEST_HPP_

class EditFoodRequest;

#include "Message.hpp"
#include <inttypes.h>

class EditFoodRequest : public Message
{
public:
    bool adding;
    int id;
    int categoryId;
    std::string name;
    std::string vendor;
    std::string gtin;
    std::string description;
    int64_t defaultUseBy;
    int amountType;
    float amount;
    float usualPrice;

    EditFoodRequest();
    virtual ~EditFoodRequest();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
