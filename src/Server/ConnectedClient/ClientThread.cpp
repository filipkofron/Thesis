/*
Copyright (C) 2014 Filip Kofron (filip.kofron.cz@gmail.com)

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

#include "ClientThread.hpp"

#include "../Network/BufferReader.hpp"
#include "../Network/ReadException.hpp"
#include "../Network/WriteException.hpp"
#include "BufferException.hpp"
#include "../Protocol/Message.hpp"
#include "../Handler/HandlerException.hpp"
#include "../Entity/DAO/DAOException.hpp"
#include <cppconn/exception.h>

#include "../Util/Log.hpp"
#include <unistd.h>
#include <jsoncpp/json/reader.h>
#include <jsoncpp/json/value.h>

void ClientThread::run(std::shared_ptr<Context> context)
{
    Log::info(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" contected!"));

    communicate(context);

    Log::info(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" Disconnected!"));
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

            if(!parseRes)
            {
                Log::error(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" parsing JSON failed!"));
                context->setFinished(true);
                continue;
            }

            std::shared_ptr<Message> msg(Message::dejsonize(root));
            if(msg.get())
            {
                Log::debug(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" Received message: ") + msg->getHeader());

                std::shared_ptr<Handler> handler(msg->createHandler());
                handler->handle(*context);
            }
            else
            {
                Log::error(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" Invalid message!"));
                context->setFinished(true);
                continue;
            }
        }
    }
    catch(ReadException &e)
    {
        Log::error(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" read error!"));
    }
    catch(WriteException &e)
    {
        Log::error(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" write error!"));
    }
    catch(BufferException &e)
    {
        Log::error(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" Buffer error: ") + std::string(e.what()));
    }
    catch(DAOException &e)
    {
        Log::error(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" DAO error: ") + std::string(e.what()));
    }
    catch(sql::SQLException &e)
    {
        Log::error(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" SQL error: ") + std::string(e.what()));
    }
    catch(HandlerException &e)
    {
        Log::error(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" Handler error: ") + std::string(e.message));
    }
    catch(std::runtime_error &e)
    {
        Log::error(std::string("[") + std::string(context->getAddr()) + std::string(":") + std::to_string(context->getPort()) + std::string("] Client #") + std::to_string(context->getServer()->getClientNum()) + std::string(" Runtime error: ") + std::string(e.what()));
    }
}
