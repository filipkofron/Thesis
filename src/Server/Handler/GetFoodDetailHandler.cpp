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

#include "GetFoodDetailHandler.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Protocol/GetFoodDetailResponse.hpp"
#include "../Network/MessageSender.hpp"
#include "../Util/Date.hpp"

#include <sstream>

GetFoodDetailHandler::GetFoodDetailHandler(GetFoodDetailRequest *request)
    : request(request)
{

}

GetFoodDetailHandler::~GetFoodDetailHandler()
{

}

void GetFoodDetailHandler::handle(Context &context)
{
    std::shared_ptr<FoodDAO> foodDao = DAOFactory::getFoodDAO();
    std::shared_ptr<ImageDAO> imageDao = DAOFactory::getImageDAO();
    std::shared_ptr<ReviewDAO> reviewDao = DAOFactory::getReviewDAO();
    std::shared_ptr<VendorDAO> vendorDao = DAOFactory::getVendorDAO();
    std::shared_ptr<UserDAO> userDao = DAOFactory::getUserDAO();
    std::shared_ptr<EditDAO> editDao = DAOFactory::getEditDAO();
    std::shared_ptr<CategoryDAO> categoryDao = DAOFactory::getCategoryDAO();

    Food food = foodDao->getFoodById(request->id);

    std::vector<Image> images = imageDao->getImageByFoodId(request->id);
    std::vector<Review> reviews = reviewDao->getReviewByFoodId(request->id);
    Vendor vendor = vendorDao->getVendorById(food.getVendorId());
    User addedBy = userDao->getUserById(food.getUserId());
    std::vector<Edit> edits = editDao->getEditByFoodId(food.getId());
    int userEditedId = addedBy.getId();

    bool editFound = false;
    std::string lastEdit;
    for(Edit &edit : edits)
    {
        if(!editFound)
        {
            lastEdit = edit.getEditedOn();
            userEditedId = edit.getUserId();
            editFound = true;
        }
        else
        {
            if(Date::unixTimeFromMysqlString(edit.getEditedOn()) > Date::unixTimeFromMysqlString(lastEdit))
            {
                lastEdit = edit.getEditedOn();
                userEditedId = edit.getUserId();
            }
        }
    }

    User editedBy = userDao->getUserById(userEditedId);
    Category category = categoryDao->getCategoryById(food.getCategoryId());

    Json::Value items;
    Json::Value obj;

    obj["id"] = food.getId();
    obj["name"] = food.getName();
    obj["category"] = category.getName();
    obj["categoryId"] = category.getId();
    obj["vendor"] = vendor.getName();
    obj["vendorId"] = food.getVendorId();
    obj["gtin"] = food.getGtin();

    Json::Value imageIds;
    int i = 0;
    for(Image &img : images)
    {
        std::stringstream ss;
        std::string strId;
        ss << img.getId();
        ss >> strId;
        imageIds[i] = strId;
        i++;
    }

    obj["imageIds"] = imageIds;
    obj["description"] = food.getDescription();
    obj["defaultUseBy"] = (Json::Int64) food.getDefaultUseBy();
    obj["amountType"] = food.getAmountMeasure();
    obj["amount"] = food.getAmount();
    obj["usualPrice"] = food.getPrice();

    Json::Value reviewObjects;
    i = 0;
    for(Review &rev : reviews)
    {
        Json::Value obj;
        obj["id"] = rev.getId();
        obj["rating"] = rev.getRating();
        User ratingUser = userDao->getUserById(rev.getUserId());
        obj["username"] = ratingUser.getUserName();
        obj["text"] = rev.getReviewText();
        reviewObjects[i] = obj;
        i++;
    }

    obj["reviews"] = reviewObjects;

    obj["addedBy"] = addedBy.getUserName();
    obj["lastEditedBy"] = editedBy.getUserName();

    items[0] = obj;

    GetFoodDetailResponse response(items);
    MessageSender::sendMessage(context, response);
}
