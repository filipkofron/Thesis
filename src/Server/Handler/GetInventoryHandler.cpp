#include "GetInventoryHandler.hpp"
#include "../Protocol/GetInventoryResponse.hpp"
#include "HandlerException.hpp"
#include "../Entity/Inventory.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Entity/DAO/InventoryDAO.hpp"
#include "../Entity/DAO/FoodDAO.hpp"
#include "../Entity/DAO/ImageDAO.hpp"
#include "../Network/MessageSender.hpp"
#include <iostream>

GetInventoryHandler::GetInventoryHandler(GetInventoryRequest *request)
    : request(request)
{

}

GetInventoryHandler::~GetInventoryHandler()
{

}

void GetInventoryHandler::handle(Context &context)
{
    if(!context.getLoggedIn())
    {
        throw HandlerException("User not logged in!");
    }

    std::shared_ptr<InventoryDAO> inventoryDao = DAOFactory::getInventoryDAO();
    std::shared_ptr<FoodDAO> foodDao = DAOFactory::getFoodDAO();
    std::shared_ptr<ImageDAO> imageDao = DAOFactory::getImageDAO();
    std::vector<Inventory> inventories;

    bool tried = false;

    if(request->direct)
    {
        std::vector<Inventory> temp = inventoryDao->getInventoryByUserId(context.getUserId());
        for(Inventory &inv : temp)
        {
            if(inv.getId() == request->id)
            {
                inventories.push_back(inv);
            }
        }
        tried = true;
    }

    if(request->food.length() > 2)
    {
        inventories = inventoryDao->searchInventoryByFoodName(context.getUserId(), request->food);
        tried = true;
    }

    std::cout << "Trying inventories searching by gtin: " << request->gtin << std::endl;

    if(request->gtin.length() > 2)
    {
        std::cout << "Requesting inventories searching by gtin: " << request->gtin << std::endl;
        inventories = inventoryDao->searchInventoryByGtin(context.getUserId(), request->gtin);
        std::cout << "Got " << inventories.size() << " results." << std::endl;
        tried = true;
    }

    if(inventories.size() < 1 && !tried)
    {
        inventories = inventoryDao->getInventoryByUserId(context.getUserId());
    }

    Json::Value items;

    int i = 0;

    for(Inventory &inv : inventories)
    {
        const Food &food = foodDao->getFoodById(inv.getFoodId());
        int foodId = food.getId();
        std::string foodName = food.getName();
        std::string foodGtin = food.getGtin();
        std::string useBy = inv.getUseBy();
        int id = inv.getId();

        Json::Value val;
        val["id"] = id;
        val["foodId"] = foodId;


        std::vector<Image> images = imageDao->getImageByFoodId(food.getId());

        if(images.size() > 0)
        {
            val["imageId"] = images.at(0).getId();
        }
        else
        {
            val["imageId"] = 0;
        }

        val["foodName"] = foodName;
        val["foodGtin"] = foodGtin;
        val["useBy"] = useBy;

        items[i] = val;
        i++;
    }

    GetInventoryResponse response(items);
    MessageSender::sendMessage(context, response);
}
