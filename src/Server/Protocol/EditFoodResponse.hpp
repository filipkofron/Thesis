#ifndef _EDITFOODRESPONSE_HPP_
#define _EDITFOODRESPONSE_HPP_

class EditFoodResponse;

#include "Message.hpp"

class EditFoodResponse : public Message
{
public:
    bool success;
    int id;

    EditFoodResponse();
    EditFoodResponse(const bool &success, const int &id);
    virtual ~EditFoodResponse();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
