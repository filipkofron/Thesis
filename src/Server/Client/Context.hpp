#ifndef _CLIENTCONTEXT_HPP_
#define _CLIENTCONTEXT_HPP_

class Context;

#include <thread>
#include <memory>
#include "Buffer.hpp"
#include "../Network/Server.hpp"

class Context
{
private:
    int clientSD;
    Buffer buffer;
    Server *server;
    std::shared_ptr<std::thread> thread;
public:
    void setClientSD(const int &clientSD);
    const int &getClientSD();
    Buffer &getBuffer();
    void setServer(Server *server);
    Server *getServer();
    void setThread(std::shared_ptr<std::thread> thread);
    std::shared_ptr<std::thread> getThread();
};

#endif
