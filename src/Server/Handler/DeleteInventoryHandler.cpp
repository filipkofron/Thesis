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
