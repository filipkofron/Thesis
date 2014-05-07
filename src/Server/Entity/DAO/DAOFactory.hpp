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

#ifndef _DAOFACTORY_HPP_
#define _DAOFACTORY_HPP_

class DAOFactory;

#include "CategoryDAO.hpp"
#include "EditDAO.hpp"
#include "FoodDAO.hpp"
#include "ImageDAO.hpp"
#include "InventoryDAO.hpp"
#include "ReviewDAO.hpp"
#include "UserDAO.hpp"
#include "VendorDAO.hpp"

#include <memory>

class DAOFactory
{
private:
    static std::shared_ptr<CategoryDAO> categoryDAO;
    static std::shared_ptr<EditDAO> editDAO;
    static std::shared_ptr<FoodDAO> foodDAO;
    static std::shared_ptr<ImageDAO> imageDAO;
    static std::shared_ptr<InventoryDAO> inventoryDAO;
    static std::shared_ptr<ReviewDAO> reviewDAO;
    static std::shared_ptr<UserDAO> userDAO;
    static std::shared_ptr<VendorDAO> vendorDAO;

public:
    static std::shared_ptr<CategoryDAO> getCategoryDAO();
    static std::shared_ptr<EditDAO> getEditDAO();
    static std::shared_ptr<FoodDAO> getFoodDAO();
    static std::shared_ptr<ImageDAO> getImageDAO();
    static std::shared_ptr<InventoryDAO> getInventoryDAO();
    static std::shared_ptr<ReviewDAO> getReviewDAO();
    static std::shared_ptr<UserDAO> getUserDAO();
    static std::shared_ptr<VendorDAO> getVendorDAO();
};

#endif
