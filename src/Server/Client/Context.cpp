#include "Context.hpp"

void Context::setClientSD(const int &clientSD)
{
    this->clientSD = clientSD;
}

const int &Context::getClientSD()
{
    return clientSD;
}

Buffer &Context::getBuffer()
{
    return buffer;
}

void Context::setServer(Server *server)
{
    this->server = server;
}

Server *Context::getServer()
{
    return server;
}

void Context::setThread(std::shared_ptr<std::thread> thread)
{
    this->thread = thread;
}

std::shared_ptr<std::thread> Context::getThread()
{
    return thread;
}
