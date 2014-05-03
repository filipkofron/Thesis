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

#ifndef _INVENTORYDAO_HPP_
#define _INVENTORYDAO_HPP_

class InventoryDAO;

#include "../Inventory.hpp"

#include <string>
#include <vector>

class InventoryDAO
{
public:
    InventoryDAO() { }
    virtual ~InventoryDAO() { }

    virtual void addInventory(Inventory &inventory, int &newId) = 0;
    virtual void deleteInventory(Inventory &inventory) = 0;
    virtual void updateInventory(const Inventory &inventory) = 0;
    virtual Inventory getInventoryById(const int &id) = 0;
    virtual std::vector<Inventory> getInventoryByUserId(const int &userId) = 0;
    virtual std::vector<Inventory> getInventoryByFoodId(const int &foodId) = 0;
    virtual std::vector<Inventory> searchInventoryByFoodName(const int &userId, const std::string &foodName) = 0;
    virtual std::vector<Inventory> searchInventoryByGtin(const int &userId, const std::string &gtin) = 0;
};

#endif
