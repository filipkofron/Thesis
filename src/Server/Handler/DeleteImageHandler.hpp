#ifndef _DELETEIMAGEHANDLER_HPP_
#define _DELETEIMAGEHANDLER_HPP_

class DeleteImageHandler;

#include "Handler.hpp"
#include "../Protocol/DeleteImageRequest.hpp"

class DeleteImageHandler : public Handler
{
private:
    DeleteImageRequest *request;
public:
    DeleteImageHandler(DeleteImageRequest *request);
    virtual ~DeleteImageHandler();
    virtual void handle(Context &context) override;
};

#endif
