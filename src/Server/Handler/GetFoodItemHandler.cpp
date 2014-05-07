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

#include "GetFoodItemHandler.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Protocol/GetFoodItemResponse.hpp"
#include "../Network/MessageSender.hpp"

GetFoodItemHandler::GetFoodItemHandler(GetFoodItemRequest *request)
    : request(request)
{
}

GetFoodItemHandler::~GetFoodItemHandler()
{

}

void GetFoodItemHandler::handle(Context &context)
{
    std::shared_ptr<FoodDAO> foodDao = DAOFactory::getFoodDAO();
    std::shared_ptr<ImageDAO> imageDao = DAOFactory::getImageDAO();

    std::vector<Food> foods;

    bool tried = false;

    if(request->direct)
    {
        foods.push_back(foodDao->getFoodById(request->id));
        tried = true;
    }

    if(request->name.length() > 2)
    {
        foods = foodDao->findFoodByName(request->name);
        tried = true;
    }

    if(request->gtin.length() > 2)
    {
        foods = foodDao->findFoodByGtin(request->gtin);
        tried = true;
    }

    if(foods.size() < 1 && !tried)
    {
        foods = foodDao->getAllFood(request->skip, 25);
    }

    Json::Value items;

    int i = 0;
    for(Food &food : foods)
    {
        Json::Value obj;

        obj["id"] = food.getId();
        obj["defaultUseBy"] = (Json::Int64) food.getDefaultUseBy();

        std::vector<Image> images = imageDao->getImageByFoodId(food.getId());

        if(images.size() > 0)
        {
            obj["imageId"] = images.at(0).getId();
        }
        else
        {
            obj["imageId"] = 0;
        }

        obj["name"] = food.getName();
        obj["desc"] = food.getDescription();
        obj["gtin"] = food.getGtin();

        items[i] = obj;
        i++;
    }

    GetFoodItemResponse response(items);
    MessageSender::sendMessage(context, response);
}
