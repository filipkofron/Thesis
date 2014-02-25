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
