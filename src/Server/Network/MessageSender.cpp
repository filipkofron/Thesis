#include "MessageSender.hpp"
#include <jsoncpp/json/writer.h>
#include <jsoncpp/json/value.h>
#include "BufferWriter.hpp"
#include <cstring>

void MessageSender::sendMessage(Context &context, Message &message)
{
    Json::FastWriter writer;
    Json::Value root;

    message.jsonize(root);
    std::string str = writer.write(root);

    int size = str.length() + 1;

    context.getBuffer().prepare(size);
    memcpy(context.getBuffer().getBytes(), str.c_str(), size);

    BufferWriter::writeBuffer(context);
}
