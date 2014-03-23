#ifndef _ADDIMAGEREQUEST_HPP_
#define _ADDIMAGEREQUEST_HPP_

class AddImageRequest;

#include "Message.hpp"

class AddImageRequest : public Message
{
public:
    int foodId;
    std::string image;

    AddImageRequest();
    virtual ~AddImageRequest();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
