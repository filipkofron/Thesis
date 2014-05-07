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

#ifndef _INVENTORY_HPP_
#define _INVENTORY_HPP_

#include <string>

class Inventory;

#include "DAO/InventoryDAO.hpp"

class Inventory
{
private:
    int id;
    int userId;
    int foodId;
    std::string useBy;
public:
    Inventory();
    Inventory(const int &id, const int &userId, const int &foodId, const std::string &useBy);

    const int &getId() const;
    const int &getUserId() const;
    const int &getFoodId() const;
    const std::string &getUseBy() const;

    void setUserId(const int &userId);
    void setFoodId(const int &foodId);
    void setUseBy(const std::string &useBy);

    static Inventory makeInventory(const int &userId, const int &foodId, const std::string &useBy, InventoryDAO &dao);
};

#endif
