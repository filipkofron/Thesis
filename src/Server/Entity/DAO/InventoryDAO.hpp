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
};

#endif
