#ifndef _MESSAGESENDER_HPP_
#define _MESSAGESENDER_HPP_

class MessageSender;

#include "../Protocol/Message.hpp"
#include "../Client/Context.hpp"

class MessageSender
{
public:
    static void sendMessage(Context &context, Message &message);
};

#endif

