#ifndef _SERVER_HPP_
#define _SERVER_HPP_

class Server;

#include <map>
#include <netinet/in.h>
#include <memory>
#include <mutex>
#include <ctime>
#include <deque>

#include "../Client/Context.hpp"

class Server
{
private:
    std::map<int, std::shared_ptr<Context>> clients;
    std::deque<std::shared_ptr<Context>> finishedContexts;
    std::mutex clientMutex;
    int listenSD;
    bool stopped;
    sockaddr_in addr;
    timeval acceptTimeout;
    unsigned long clientNum;
    void addrToStr(char *buffer, int bufLen, const sockaddr_in &addr);
    void handleClient(int clientSD, sockaddr_in clientAddr);
    void clearFinishedContexts();
public:
    Server();
    void initialize();
    void run();
    void setStopped(const bool &stopped);
    std::mutex &getClientMutex();
    std::map<int, std::shared_ptr<Context>> &getClients();
    std::deque<std::shared_ptr<Context>> &getFinishedContexts();
    const unsigned long &getClientNum();
};

#endif
