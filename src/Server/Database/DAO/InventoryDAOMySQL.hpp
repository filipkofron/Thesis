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

#ifndef _INVENTORYDAOMYSQL_HPP_
#define _INVENTORYDAOMYSQL_HPP_

class InventoryDAOMySQL;

#include "../../Entity/DAO/InventoryDAO.hpp"

class InventoryDAOMySQL : public InventoryDAO
{
public:
    InventoryDAOMySQL() { }
    virtual ~InventoryDAOMySQL() { }

    virtual void addInventory(Inventory &inventory, int &newId);
    virtual void deleteInventory(Inventory &inventory);
    virtual void updateInventory(const Inventory &inventory);
    virtual Inventory getInventoryById(const int &id);
    virtual std::vector<Inventory> getInventoryByUserId(const int &userId);
    virtual std::vector<Inventory> getInventoryByFoodId(const int &foodId);
    virtual std::vector<Inventory> searchInventoryByFoodName(const int &userId, const std::string &foodName);
    virtual std::vector<Inventory> searchInventoryByGtin(const int &userId, const std::string &gtin);
};

#endif
