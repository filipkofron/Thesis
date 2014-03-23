#ifndef _DELETEIMAGERESPONSE_HPP_
#define _DELETEIMAGERESPONSE_HPP_

class DeleteImageResponse;

#include "Message.hpp"

class DeleteImageResponse : public Message
{
public:
    bool success;

    DeleteImageResponse();
    DeleteImageResponse(const bool &success);
    virtual ~DeleteImageResponse();
    static std::string getStaticHeader();
    virtual std::string getHeader() override;
    virtual Handler *createHandler() override;
protected:
    virtual void _dejsonize(Json::Value &content) override;
    virtual void _jsonize(Json::Value &content) override;
};

#endif
