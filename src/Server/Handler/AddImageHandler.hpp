#ifndef _ADDIMAGEHANDLER_HPP_
#define _ADDIMAGEHANDLER_HPP_

class AddImageHandler;

#include "Handler.hpp"
#include "../Protocol/AddImageRequest.hpp"

class AddImageHandler : public Handler
{
private:
    AddImageRequest *request;
public:
    AddImageHandler(AddImageRequest *request);
    virtual ~AddImageHandler();
    virtual void handle(Context &context) override;
};

#endif
