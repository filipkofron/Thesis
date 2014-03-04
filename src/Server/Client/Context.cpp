#include "Context.hpp"

Context::Context()
    : finished(false), loggedIn(false), username("_no_user_")
{
}

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

char *Context::getAddr()
{
    return addr;
}

void Context::setPort(const int &port)
{
    this->port = port;
}

const int &Context::getPort()
{
    return port;
}

const bool &Context::getFinished()
{
    return finished;
}

void Context::setFinished(const bool &finished)
{
    this->finished = finished;
}

const bool &Context::getLoggedIn()
{
    return loggedIn;
}

void Context::setLoggedIn(const bool &loggedIn)
{
    this->loggedIn = loggedIn;
}

const std::string &Context::getUsername()
{
    return username;
}

void Context::setUsername(const std::string &username)
{
    this->username = username;
}