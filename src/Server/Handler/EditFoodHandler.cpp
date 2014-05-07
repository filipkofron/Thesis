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

#include "EditFoodHandler.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Entity/DAO/DAOException.hpp"
#include "../Protocol/EditFoodResponse.hpp"
#include "../Network/MessageSender.hpp"
#include "../Util/Date.hpp"

EditFoodHandler::EditFoodHandler(EditFoodRequest *request)
    : request(request)
{
}

EditFoodHandler::~EditFoodHandler()
{
}

void EditFoodHandler::handle(Context &context)
{
    if(!context.getLoggedIn())
    {
        EditFoodResponse response(false, 0);
        MessageSender::sendMessage(context, response);
    }

    std::shared_ptr<FoodDAO> foodDao = DAOFactory::getFoodDAO();
    std::shared_ptr<VendorDAO> vendorDao = DAOFactory::getVendorDAO();
    std::shared_ptr<CategoryDAO> categoryDao = DAOFactory::getCategoryDAO();
    std::shared_ptr<EditDAO> editDao = DAOFactory::getEditDAO();

    Vendor vendor;
    try
    {
        vendor = vendorDao->getVendorByName(request->vendor);
    }
    catch(DAOException &e)
    {
        vendor = Vendor::makeVendor(request->vendor, *vendorDao);
    }

    Food food;
    Category category = categoryDao->getCategoryById(request->categoryId);

    if(request->adding)
    {
        food = Food::makeFood(request->gtin, request->name, request->description, category.getId(), request->defaultUseBy, request->amountType, request->amount, context.getUserId(), request->usualPrice, vendor.getId(), *foodDao);
    }
    else
    {
        food = foodDao->getFoodById(request->id);
        food.setCategoryId(category.getId());
        food.setName(request->name);
        food.setVendorId(vendor.getId());
        food.setGtin(request->gtin);
        food.setDescription(request->description);
        food.setDefaultUseBy(request->defaultUseBy);
        food.setAmountMeasure(request->amountType);
        food.setAmount(request->amount);
        food.setPrice(request->usualPrice);

        foodDao->updateFood(food);

    }

    const char *msg = request->adding ? "User added food." : "User edited food.";

    Edit edit = Edit::makeEdit(context.getUserId(), food.getId(), Date::unixTimeToMysqlString(Date::currentTimeMilis() / 1000), msg, *editDao);

    EditFoodResponse response(true, food.getId());
    MessageSender::sendMessage(context, response);
}
