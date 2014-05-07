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

#include "DeleteInventoryHandler.hpp"
#include "../Protocol/DeleteInventoryResponse.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Network/MessageSender.hpp"
#include "HandlerException.hpp"

DeleteInventoryHandler::DeleteInventoryHandler(DeleteInventoryRequest *request)
    : request(request)
{

}

DeleteInventoryHandler::~DeleteInventoryHandler()
{

}

void DeleteInventoryHandler::handle(Context &context)
{
    if(!context.getLoggedIn())
    {
        DeleteInventoryResponse response(false);
        MessageSender::sendMessage(context, response);
    }

    std::shared_ptr<InventoryDAO> inventoryDao = DAOFactory::getInventoryDAO();

    Inventory inventory = inventoryDao->getInventoryById(request->id);
    if(inventory.getUserId() != context.getUserId())
    {
        throw HandlerException("User is not owner of edited inventory!");
    }

    inventoryDao->deleteInventory(inventory);

    DeleteInventoryResponse response(true);
    MessageSender::sendMessage(context, response);
}
