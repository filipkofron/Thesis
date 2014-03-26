#include "InventoryDAOMySQL.hpp"

#include "../MySQLManager.hpp"
#include "../../Entity/DAO/DAOException.hpp"

#include <cppconn/prepared_statement.h>
#include <cppconn/resultset.h>

static const char *ADD_INVENTORY = "INSERT INTO Inventory (user_id, food_id, use_by) VALUES (?, ?, ?)";
static const char *DELETE_INVENTORY = "DELETE FROM Inventory WHERE id = ?";
static const char *UPDATE_INVENTORY = "UPDATE Inventory SET user_id = ?, food_id = ?, use_by = ? WHERE id = ?";
static const char *INVENTORY_BY_ID = "SELECT id, user_id, food_id, use_by FROM Inventory WHERE id = ?";
static const char *INVENTORIES_BY_USERID = "SELECT id, user_id, food_id, use_by FROM Inventory WHERE user_id = ?";
static const char *INVENTORIES_BY_FOODID = "SELECT id, user_id, food_id, use_by FROM Inventory WHERE food_id = ?";
static const char *INVENTORIES_SEARCH_FOODNAME = "SELECT Inventory.id, Inventory.user_id, Inventory.food_id, Inventory.use_by FROM Inventory JOIN Food ON Inventory.food_id = Food.id WHERE Inventory.user_id = ? AND name COLLATE UTF8_GENERAL_CI LIKE ?";
static const char *INVENTORIES_SEARCH_GTIN = "SELECT Inventory.id, Inventory.user_id, Inventory.food_id, Inventory.use_by FROM Inventory JOIN Food ON Inventory.food_id = Food.id WHERE Inventory.user_id = ? AND gtin COLLATE UTF8_GENERAL_CI LIKE ?";

void InventoryDAOMySQL::addInventory(Inventory &inventory, int &newId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(ADD_INVENTORY));

    ps->setInt(1, inventory.getUserId());
    ps->setInt(2, inventory.getFoodId());
    ps->setString(3, inventory.getUseBy());

    ps->execute();

    newId = MySQLManager::lastGeneratedId(ch.conn);
}

void InventoryDAOMySQL::deleteInventory(Inventory &inventory)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(DELETE_INVENTORY));

    ps->setInt(1, inventory.getId());

    ps->execute();
}

void InventoryDAOMySQL::updateInventory(const Inventory &inventory)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(UPDATE_INVENTORY));

    ps->setInt(1, inventory.getUserId());
    ps->setInt(2, inventory.getFoodId());
    ps->setString(3, inventory.getUseBy());
    ps->setInt(4, inventory.getId());

    ps->executeUpdate();
}

Inventory InventoryDAOMySQL::getInventoryById(const int &id)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(INVENTORY_BY_ID));

    ps->setInt(1, id);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    if(!res->next())
    {
        throw DAOException("Inventory not found!");
    }
    return Inventory(res->getInt(1), res->getInt(2), res->getInt(3), res->getString(4));
}

std::vector<Inventory> InventoryDAOMySQL::getInventoryByUserId(const int &userId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(INVENTORIES_BY_USERID));

    ps->setInt(1, userId);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Inventory> inventories;
    while(res->next())
    {
        inventories.push_back(Inventory(res->getInt(1), res->getInt(2), res->getInt(3), res->getString(4)));
    }
    return inventories;
}

std::vector<Inventory> InventoryDAOMySQL::getInventoryByFoodId(const int &foodId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(INVENTORIES_BY_FOODID));

    ps->setInt(1, foodId);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Inventory> inventories;
    while(res->next())
    {
        inventories.push_back(Inventory(res->getInt(1), res->getInt(2), res->getInt(3), res->getString(4)));
    }
    return inventories;
}

std::vector<Inventory> InventoryDAOMySQL::searchInventoryByFoodName(const int &userId, const std::string &foodName)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(INVENTORIES_SEARCH_FOODNAME));

    ps->setInt(1, userId);
    ps->setString(2, "%" + foodName + "%");

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Inventory> inventories;
    while(res->next())
    {
        inventories.push_back(Inventory(res->getInt(1), res->getInt(2), res->getInt(3), res->getString(4)));
    }

    return inventories;
}

std::vector<Inventory> InventoryDAOMySQL::searchInventoryByGtin(const int &userId, const std::string &gtin)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(INVENTORIES_SEARCH_GTIN));

    ps->setInt(1, userId);
    ps->setString(2, "%" + gtin + "%");

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Inventory> inventories;
    while(res->next())
    {
        inventories.push_back(Inventory(res->getInt(1), res->getInt(2), res->getInt(3), res->getString(4)));
    }
    return inventories;
}
