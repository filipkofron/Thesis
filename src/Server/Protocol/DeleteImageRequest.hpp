#ifndef _DELETEIMAGEREQUEST_HPP_
#define _DELETEIMAGEREQUEST_HPP_

class DeleteImageRequest;

#include "Message.hpp"

class DeleteImageRequest : public Message
{
public:
    int id;

    DeleteImageRequest();
    virtual ~DeleteImageRequest();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
