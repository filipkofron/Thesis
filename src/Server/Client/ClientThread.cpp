#include "ClientThread.hpp"

#include "../Network/BufferReader.hpp"
#include "../Network/ReadException.hpp"
#include "../Network/WriteException.hpp"
#include "BufferException.hpp"
#include "../Protocol/Message.hpp"

#include <iostream>
#include <unistd.h>
#include <jsoncpp/json/reader.h>
#include <jsoncpp/json/value.h>

void ClientThread::run(std::shared_ptr<Context> context)
{
    std::cout << "[" << context->getAddr() << ":" << context->getPort() << "] Client #" << context->getServer()->getClientNum() << " contected!" << std::endl;

    communicate(context);

    std::cout << "[" << context->getAddr() << ":" << context->getPort() << "] Client #" << context->getServer()->getClientNum() << " Disconnected!" << std::endl;
    /* Clean up when finished */

    close(context->getClientSD());

    std::lock_guard<std::mutex> lock(context->getServer()->getClientMutex());
    context->getServer()->getFinishedContexts().push_front(context);
}

void ClientThread::communicate(std::shared_ptr<Context> context)
{
    try
    {
        Json::Reader reader;
        Json::Value root;
        while(!context->getFinished())
        {
            BufferReader::readBuffer(*context);


            bool parseRes = reader.parse(std::string(context->getBuffer().getBytes()), root);

            std::cout << "parseRes: " << parseRes << std::endl;

            if(!parseRes)
            {
                std::cout << "[" << context->getAddr() << ":" << context->getPort() << "] Client #" << context->getServer()->getClientNum() << " Parsing JSON failed!" << std::endl;
                context->setFinished(true);
                continue;
            }
            else
            {
                std::cout << "[" << context->getAddr() << ":" << context->getPort() << "] Client #" << context->getServer()->getClientNum() << " Parsing JSON successful!" << std::endl;
            }

            std::shared_ptr<Message> msg(Message::dejsonize(root));
            if(msg.get())
            {
                std::cout << "Got message: '" << typeid(msg).name() << "'" << std::endl;


                std::cout << ">>>> DEBUG >>>> SLOW DOWN FOR 3s" << std::endl;
                sleep(3);

                std::shared_ptr<Handler> handler(msg->createHandler());
                handler->handle(*context);
            }
            else
            {
                std::cout << "[" << context->getAddr() << ":" << context->getPort() << "] Client #" << context->getServer()->getClientNum() << " Invalid message!" << std::endl;
                context->setFinished(true);
                continue;
            }
        }
    }
    catch(ReadException &e)
    {
        std::cout << "[" << context->getAddr() << ":" << context->getPort() << "] Client #" << context->getServer()->getClientNum() << " read error!" << std::endl;
    }
    catch(WriteException &e)
    {
        std::cout << "[" << context->getAddr() << ":" << context->getPort() << "] Client #" << context->getServer()->getClientNum() << " write error!" << std::endl;
    }
    catch(BufferException &e)
    {
        std::cout << "[" << context->getAddr() << ":" << context->getPort() << "] Client #" << context->getServer()->getClientNum() << ": Buffer error:" << e.what() << std::endl;
    }
    catch(std::runtime_error &e)
    {
        std::cout << "[" << context->getAddr() << ":" << context->getPort() << "] Client #" << context->getServer()->getClientNum() << ": Runtime error:" << e.what() << std::endl;
    }
}
