#include "ClientThread.hpp"

#include <iostream>
#include <unistd.h>

void ClientThread::run(std::shared_ptr<Context> context)
{
    std::cout << "Hello from client thread!" << std::endl;

    sleep(1);

    std::cout << "Done job!" << std::endl;
    /* Clean up when finished */

    close(context->getClientSD());

    std::lock_guard<std::mutex> lock(context->getServer()->getClientMutex());
    context->getServer()->getFinishedContexts().push_front(context);
}
