#ifndef _SERVER_HPP_
#define _SERVER_HPP_

class Server;

#include <map>
#include <netinet/in.h>
#include <memory>
#include <ctime>

#include "../Client/Context.hpp"

class Server
{
private:
    std::map<int, std::shared_ptr<Context>> clients;
    int listenSD;
    bool stopped;
    sockaddr_in addr;
    timeval timeout;
    fd_set masterSet;
    fd_set workingSet;
    int maxSD;
public:
    Server();
    void initialize();
    void run();
    void setStopped(const bool &stopped);
};

#endif
