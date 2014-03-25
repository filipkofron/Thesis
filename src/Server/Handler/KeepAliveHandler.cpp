#include "KeepAliveHandler.hpp"
#include "../Network/MessageSender.hpp"

KeepAliveHandler::KeepAliveHandler(KeepAlive *request)
    : request(request)
{

}

KeepAliveHandler::~KeepAliveHandler()
{

}

void KeepAliveHandler::handle(Context &context)
{
    KeepAlive response;
    MessageSender::sendMessage(context, response);
}
