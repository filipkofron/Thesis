#include "EditInventoryHandler.hpp"
#include "../Protocol/EditInventoryResponse.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Network/MessageSender.hpp"
#include "../Util/Date.hpp"
#include "HandlerException.hpp"

EditInventoryHandler::EditInventoryHandler(EditInventoryRequest *request)
    : request(request)
{

}

EditInventoryHandler::~EditInventoryHandler()
{

}

void EditInventoryHandler::handle(Context &context)
{
    if(!context.getLoggedIn())
    {
        EditInventoryResponse response(false);
        MessageSender::sendMessage(context, response);
    }
    std::shared_ptr<InventoryDAO> inventoryDao = DAOFactory::getInventoryDAO();
    std::shared_ptr<FoodDAO> foodDao = DAOFactory::getFoodDAO();

    std::string date = Date::unixTimeToMysqlString(request->useBy);

    // Does the food exist? if not, throw error
    foodDao->getFoodById(request->foodId);

    if(request->adding)
    {
        int count = request->count;
        if(count > 100)
        {
            count = 100;
        }
        for(int i = 0; i < count; i++)
        {
            Inventory::makeInventory(context.getUserId(), request->foodId, date, *inventoryDao);
        }
    }
    else
    {
        Inventory inv = inventoryDao->getInventoryById(request->id);
        inv.setFoodId(request->foodId);
        inv.setUseBy(date);
        if(inv.getUserId() != context.getUserId())
        {
            throw HandlerException("User is not owner of edited inventory!");
        }
        inventoryDao->updateInventory(inv);
    }

    EditInventoryResponse response(true);
    MessageSender::sendMessage(context, response);
}
