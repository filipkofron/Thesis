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

#include "GetFoodBaseHandler.hpp"
#include "../Protocol/GetFoodBaseResponse.hpp"
#include "../Network/MessageSender.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include <vector>

GetFoodBaseHandler::GetFoodBaseHandler(GetFoodBaseRequest *request)
    : request(request)
{

}

GetFoodBaseHandler::~GetFoodBaseHandler()
{

}

void GetFoodBaseHandler::handle(Context &context)
{
    std::shared_ptr<VendorDAO> vendorDao = DAOFactory::getVendorDAO();
    std::shared_ptr<CategoryDAO> categoryDAO = DAOFactory::getCategoryDAO();

    std::vector<Vendor> vendors = vendorDao->getAllVendors();
    std::vector<Category> categories = categoryDAO->getAllCategories();

    Json::Value vendorArray;
    Json::Value categoryArray;

    int i = 0;
    for(Vendor &vendor : vendors)
    {
        Json::Value obj;
        obj["id"] = vendor.getId();
        obj["name"] = vendor.getName();
        vendorArray[i] = obj;
        i++;
    }

    i = 0;
    for(Category &category : categories)
    {
        Json::Value obj;
        obj["id"] = category.getId();
        obj["name"] = category.getName();
        categoryArray[i] = obj;
        i++;
    }

    GetFoodBaseResponse response(categoryArray, vendorArray);
    MessageSender::sendMessage(context, response);
}
