#ifndef _CLIENTTHREAD_HPP_
#define _CLIENTTHREAD_HPP_

class ClientThread;

#include "Context.hpp"

class ClientThread
{
public:
    static void run(std::shared_ptr<Context> context);
private:
    static void communicate(std::shared_ptr<Context> context);
};

#endif
