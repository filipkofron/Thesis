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

#ifndef _FOODDAOMYSQL_HPP_
#define _FOODDAOMYSQL_HPP_

class FoodDAOMySQL;

#include "../../Entity/DAO/FoodDAO.hpp"

class FoodDAOMySQL : public FoodDAO
{
public:
    FoodDAOMySQL() { }
    virtual ~FoodDAOMySQL() { }

    virtual void addFood(Food &food, int &newId);
    virtual void deleteFood(Food &food);
    virtual void updateFood(const Food &food);
    virtual Food getFoodById(const int &id);
    virtual Food getFoodByGtin(const std::string &gtin);
    virtual std::vector<Food> findFoodByGtin(const std::string &gtin);
    virtual std::vector<Food> findFoodByName(const std::string &name);
    virtual std::vector<Food> getFoodByUserId(const int &userId);
    virtual std::vector<Food> getFoodByCategoryId(const int &categoryId);
    virtual std::vector<Food> getFoodByVendorId(const int &vendorId);
    virtual std::vector<Food> getAllFood(const int &offset, const int &max);
};

#endif
