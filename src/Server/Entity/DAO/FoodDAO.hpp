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

#ifndef _FOODDAO_HPP_
#define _FOODDAO_HPP_

class FoodDAO;

#include "../Food.hpp"

#include <string>
#include <vector>

class FoodDAO
{
public:
    FoodDAO() { }
    virtual ~FoodDAO() { }

    virtual void addFood(Food &food, int &newId) = 0;
    virtual void deleteFood(Food &food) = 0;
    virtual void updateFood(const Food &food) = 0;
    virtual Food getFoodById(const int &id) = 0;
    virtual Food getFoodByGtin(const std::string &gtin) = 0;
    virtual std::vector<Food> findFoodByGtin(const std::string &gtin) = 0;
    virtual std::vector<Food> findFoodByName(const std::string &name) = 0;
    virtual std::vector<Food> getFoodByUserId(const int &userId) = 0;
    virtual std::vector<Food> getFoodByCategoryId(const int &categoryId) = 0;
    virtual std::vector<Food> getFoodByVendorId(const int &vendorId) = 0;
    virtual std::vector<Food> getAllFood(const int &offset, const int &max) = 0;
};

#endif
