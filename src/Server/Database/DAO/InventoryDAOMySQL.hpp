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
};

#endif
