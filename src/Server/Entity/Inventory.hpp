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
