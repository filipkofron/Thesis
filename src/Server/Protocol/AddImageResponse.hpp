#ifndef _ADDIMAGERESPONSE_HPP_
#define _ADDIMAGERESPONSE_HPP_

class AddImageResponse;

#include "Message.hpp"

class AddImageResponse : public Message
{
public:
    bool success;

    AddImageResponse();
    AddImageResponse(const bool &success);
    virtual ~AddImageResponse();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif

