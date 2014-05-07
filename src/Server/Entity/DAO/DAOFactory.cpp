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

#include "DAOFactory.hpp"

#include "../../Database/DAO/CategoryDAOMySQL.hpp"
#include "../../Database/DAO/EditDAOMySQL.hpp"
#include "../../Database/DAO/FoodDAOMySQL.hpp"
#include "../../Database/DAO/ImageDAOMySQL.hpp"
#include "../../Database/DAO/InventoryDAOMySQL.hpp"
#include "../../Database/DAO/ReviewDAOMySQL.hpp"
#include "../../Database/DAO/UserDAOMySQL.hpp"
#include "../../Database/DAO/VendorDAOMySQL.hpp"

std::shared_ptr<CategoryDAO> DAOFactory::categoryDAO(new CategoryDAOMySQL());
std::shared_ptr<EditDAO> DAOFactory::editDAO(new EditDAOMySQL());
std::shared_ptr<FoodDAO> DAOFactory::foodDAO(new FoodDAOMySQL());
std::shared_ptr<ImageDAO> DAOFactory::imageDAO(new ImageDAOMySQL());
std::shared_ptr<InventoryDAO> DAOFactory::inventoryDAO(new InventoryDAOMySQL());
std::shared_ptr<ReviewDAO> DAOFactory::reviewDAO(new ReviewDAOMySQL());
std::shared_ptr<UserDAO> DAOFactory::userDAO(new UserDAOMySQL());
std::shared_ptr<VendorDAO> DAOFactory::vendorDAO(new VendorDAOMySQL());

std::shared_ptr<CategoryDAO> DAOFactory::getCategoryDAO()
{
    return categoryDAO;
}

std::shared_ptr<EditDAO> DAOFactory::getEditDAO()
{
    return editDAO;
}

std::shared_ptr<FoodDAO> DAOFactory::getFoodDAO()
{
    return foodDAO;
}

std::shared_ptr<ImageDAO> DAOFactory::getImageDAO()
{
    return imageDAO;
}

std::shared_ptr<InventoryDAO> DAOFactory::getInventoryDAO()
{
    return inventoryDAO;
}

std::shared_ptr<ReviewDAO> DAOFactory::getReviewDAO()
{
    return reviewDAO;
}

std::shared_ptr<UserDAO> DAOFactory::getUserDAO()
{
    return userDAO;
}

std::shared_ptr<VendorDAO> DAOFactory::getVendorDAO()
{
    return vendorDAO;
}
