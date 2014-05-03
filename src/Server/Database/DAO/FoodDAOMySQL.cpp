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

#include "FoodDAOMySQL.hpp"

#include "../MySQLManager.hpp"
#include "../../Entity/DAO/DAOException.hpp"

#include <cppconn/prepared_statement.h>
#include <cppconn/resultset.h>

#include <inttypes.h>
#include <sstream>

static const char *ADD_FOOD = "INSERT INTO Food (gtin, name, description, category_id, default_use_by, amount_measure, amount, user_id, price, vendor_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
static const char *DELETE_FOOD = "DELETE FROM Food WHERE id = ?";
static const char *UPDATE_FOOD = "UPDATE Food SET gtin = ?, name = ?, description = ?, category_id = ?, default_use_by = ?, amount_measure = ?, amount = ?, user_id = ?, price = ?, vendor_id = ? WHERE id = ?";
static const char *FOOD_BY_ID = "SELECT id, gtin, name, description, category_id, default_use_by, amount_measure, amount, user_id, price, vendor_id FROM Food WHERE id = ?";
static const char *FOOD_BY_GTIN = "SELECT id, gtin, name, description, category_id, default_use_by, amount_measure, amount, user_id, price, vendor_id FROM Food WHERE gtin = ?";
static const char *FIND_FOOD_BY_NAME = "SELECT id, gtin, name, description, category_id, default_use_by, amount_measure, amount, user_id, price, vendor_id FROM Food WHERE name COLLATE UTF8_GENERAL_CI LIKE ?";
static const char *FIND_FOOD_BY_GTIN = "SELECT id, gtin, name, description, category_id, default_use_by, amount_measure, amount, user_id, price, vendor_id FROM Food WHERE gtin COLLATE UTF8_GENERAL_CI LIKE ?";
static const char *FOOD_BY_USERID = "SELECT id, gtin, name, description, category_id, default_use_by, amount_measure, amount, user_id, price, vendor_id FROM Food WHERE user_id = ?";
static const char *FOOD_BY_CATEGORYID = "SELECT id, gtin, name, description, category_id, default_use_by, amount_measure, amount, user_id, price, vendor_id FROM Food WHERE category_id = ?";
static const char *FOOD_BY_VENDORID = "SELECT id, gtin, name, description, category_id, default_use_by, amount_measure, amount, user_id, price, vendor_id FROM Food WHERE vendor_id = ?";
static const char *ALL_FOOD = "SELECT id, gtin, name, description, category_id, default_use_by, amount_measure, amount, user_id, price, vendor_id FROM Food LIMIT ?, ?";


void FoodDAOMySQL::addFood(Food &food, int &newId)
{

    bool exists = false;

    try
    {
        getFoodByGtin(food.getGtin());
        exists = true;
    }
    catch(DAOException &e)
    {
    }

    if(exists)
    {
        throw DAOException("Food already exists!");
    }

    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(ADD_FOOD));

    ps->setString(1, food.getGtin());
    ps->setString(2, food.getName());
    ps->setString(3, food.getDescription());
    ps->setInt(4, food.getCategoryId());

    std::stringstream ss;

    ss << food.getDefaultUseBy();
    std::string defaultUseString;

    ss >> defaultUseString;

    ps->setString(5, defaultUseString);
    ps->setInt(6, food.getAmountMeasure());
    ps->setDouble(7, food.getAmount());
    ps->setInt(8, food.getUserId());
    ps->setDouble(9, food.getPrice());
    ps->setInt(10, food.getVendorId());

    ps->execute();

    newId = MySQLManager::lastGeneratedId(ch.conn);
}

void FoodDAOMySQL::deleteFood(Food &food)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(DELETE_FOOD));

    ps->setInt(1, food.getId());

    ps->execute();
}

void FoodDAOMySQL::updateFood(const Food &food)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(UPDATE_FOOD));

    ps->setString(1, food.getGtin());
    ps->setString(2, food.getName());
    ps->setString(3, food.getDescription());
    ps->setInt(4, food.getCategoryId());

    std::stringstream ss;

    ss << food.getDefaultUseBy();
    std::string defaultUseString;

    ss >> defaultUseString;

    ps->setString(5, defaultUseString);
    ps->setInt(6, food.getAmountMeasure());
    ps->setDouble(7, food.getAmount());
    ps->setInt(8, food.getUserId());
    ps->setDouble(9, food.getPrice());
    ps->setInt(10, food.getVendorId());

    ps->setInt(11, food.getId());

    ps->executeUpdate();
}

Food FoodDAOMySQL::getFoodById(const int &id)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(FOOD_BY_ID));

    ps->setInt(1, id);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    if(!res->next())
    {
        throw DAOException("Food not found!");
    }

    std::stringstream ss;
    ss << res->getString(6);
    int64_t defaultUseLong;
    ss >> defaultUseLong;

    return Food(res->getInt(1), res->getString(2), res->getString(3), res->getString(4), res->getInt(5), defaultUseLong, res->getInt(7), res->getDouble(8), res->getInt(9), res->getDouble(10), res->getInt(11));
}

Food FoodDAOMySQL::getFoodByGtin(const std::string &gtin)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(FOOD_BY_GTIN));

    ps->setString(1, gtin);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    if(!res->next())
    {
        throw DAOException("Food not found!");
    }

    std::stringstream ss;
    ss << res->getString(6);
    int64_t defaultUseLong;
    ss >> defaultUseLong;

    return Food(res->getInt(1), res->getString(2), res->getString(3), res->getString(4), res->getInt(5), defaultUseLong, res->getInt(7), res->getDouble(8), res->getInt(9), res->getDouble(10), res->getInt(11));
}

std::vector<Food> FoodDAOMySQL::findFoodByGtin(const std::string &gtin)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(FIND_FOOD_BY_GTIN));

    ps->setString(1, "%" + gtin + "%");

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Food> food;

    while(res->next())
    {
        std::stringstream ss;
        ss << res->getString(6);
        int64_t defaultUseLong;
        ss >> defaultUseLong;
        food.push_back(Food(res->getInt(1), res->getString(2), res->getString(3), res->getString(4), res->getInt(5), defaultUseLong, res->getInt(7), res->getDouble(8), res->getInt(9), res->getDouble(10), res->getInt(11)));
    }

    return food;
}

std::vector<Food> FoodDAOMySQL::findFoodByName(const std::string &name)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(FIND_FOOD_BY_NAME));

    ps->setString(1, "%" + name + "%");

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Food> food;

    while(res->next())
    {
        std::stringstream ss;
        ss << res->getString(6);
        int64_t defaultUseLong;
        ss >> defaultUseLong;
        food.push_back(Food(res->getInt(1), res->getString(2), res->getString(3), res->getString(4), res->getInt(5), defaultUseLong, res->getInt(7), res->getDouble(8), res->getInt(9), res->getDouble(10), res->getInt(11)));
    }

    return food;
}

std::vector<Food> FoodDAOMySQL::getFoodByUserId(const int &userId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(FOOD_BY_USERID));

    ps->setInt(1, userId);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Food> food;

    while(res->next())
    {
        std::stringstream ss;
        ss << res->getString(6);
        int64_t defaultUseLong;
        ss >> defaultUseLong;
        food.push_back(Food(res->getInt(1), res->getString(2), res->getString(3), res->getString(4), res->getInt(5), defaultUseLong, res->getInt(7), res->getDouble(8), res->getInt(9), res->getDouble(10), res->getInt(11)));
    }

    return food;
}

std::vector<Food> FoodDAOMySQL::getFoodByCategoryId(const int &categoryId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(FOOD_BY_CATEGORYID));

    ps->setInt(1, categoryId);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Food> food;

    while(res->next())
    {
        std::stringstream ss;
        ss << res->getString(6);
        int64_t defaultUseLong;
        ss >> defaultUseLong;
        food.push_back(Food(res->getInt(1), res->getString(2), res->getString(3), res->getString(4), res->getInt(5), defaultUseLong, res->getInt(7), res->getDouble(8), res->getInt(9), res->getDouble(10), res->getInt(11)));
    }

    return food;
}

std::vector<Food> FoodDAOMySQL::getFoodByVendorId(const int &vendorId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(FOOD_BY_VENDORID));

    ps->setInt(1, vendorId);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Food> food;

    while(res->next())
    {
        std::stringstream ss;
        ss << res->getString(6);
        int64_t defaultUseLong;
        ss >> defaultUseLong;
        food.push_back(Food(res->getInt(1), res->getString(2), res->getString(3), res->getString(4), res->getInt(5), defaultUseLong, res->getInt(7), res->getDouble(8), res->getInt(9), res->getDouble(10), res->getInt(11)));
    }

    return food;
}

std::vector<Food> FoodDAOMySQL::getAllFood(const int &offset, const int &max)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(ALL_FOOD));

    ps->setInt(1, offset);
    ps->setInt(2, max);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Food> food;

    while(res->next())
    {
        std::stringstream ss;
        ss << res->getString(6);
        int64_t defaultUseLong;
        ss >> defaultUseLong;
        food.push_back(Food(res->getInt(1), res->getString(2), res->getString(3), res->getString(4), res->getInt(5), defaultUseLong, res->getInt(7), res->getDouble(8), res->getInt(9), res->getDouble(10), res->getInt(11)));
    }

    return food;
}
