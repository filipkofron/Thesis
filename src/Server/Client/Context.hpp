#ifndef _CLIENTCONTEXT_HPP_
#define _CLIENTCONTEXT_HPP_

class Context;

#include <thread>
#include <array>
#include <memory>
#include "Buffer.hpp"
#include "../Network/Server.hpp"

#define CONTEXT_ADDR_BUFFER_LEN 16

class Context
{
private:
    char addr[CONTEXT_ADDR_BUFFER_LEN];
    int port;
    bool finished;
    int clientSD;
    bool loggedIn;
    std::string username;
    int userId;
    Buffer buffer;
    Server *server;
    std::shared_ptr<std::thread> thread;
public:
    Context();
    void setClientSD(const int &clientSD);
    const int &getClientSD();
    Buffer &getBuffer();
    void setServer(Server *server);
    Server *getServer();
    void setThread(std::shared_ptr<std::thread> thread);
    std::shared_ptr<std::thread> getThread();
    char *getAddr();
    void setPort(const int &port);
    const int &getPort();
    const bool &getFinished();
    void setFinished(const bool &finished);
    const bool &getLoggedIn();
    void setLoggedIn(const bool &loggedIn);
    const std::string &getUsername();
    void setUsername(const std::string &username);
    const int &getUserId();
    void setUserId(const int &userId);
};

#endif
