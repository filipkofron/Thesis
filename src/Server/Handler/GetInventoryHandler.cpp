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

#include "GetInventoryHandler.hpp"
#include "../Protocol/GetInventoryResponse.hpp"
#include "HandlerException.hpp"
#include "../Entity/Inventory.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Entity/DAO/InventoryDAO.hpp"
#include "../Entity/DAO/FoodDAO.hpp"
#include "../Entity/DAO/ImageDAO.hpp"
#include "../Network/MessageSender.hpp"

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

    if(request->gtin.length() > 2)
    {
        inventories = inventoryDao->searchInventoryByGtin(context.getUserId(), request->gtin);
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
