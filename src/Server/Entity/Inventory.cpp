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

#include "Inventory.hpp"

Inventory::Inventory()
{

}

Inventory::Inventory(const int &id, const int &userId, const int &foodId, const std::string &useBy)
    : id(id), userId(userId), foodId(foodId), useBy(useBy)
{

}

const int &Inventory::getId() const
{
    return id;
}

const int &Inventory::getUserId() const
{
    return userId;
}

const int &Inventory::getFoodId() const
{
    return foodId;
}

const std::string &Inventory::getUseBy() const
{
    return useBy;
}

void Inventory::setUserId(const int &userId)
{
    this->userId = userId;
}

void Inventory::setFoodId(const int &foodId)
{
    this->foodId = foodId;
}

void Inventory::setUseBy(const std::string &useBy)
{
    this->useBy = useBy;
}

Inventory Inventory::makeInventory(const int &userId, const int &foodId, const std::string &useBy, InventoryDAO &dao)
{
    Inventory inventory(0, userId, foodId, useBy);

    int newId = 0;
    dao.addInventory(inventory, newId);

    inventory.id = newId;

    return inventory;
}
