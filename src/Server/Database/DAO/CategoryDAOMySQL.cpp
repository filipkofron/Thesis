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

#include <cppconn/prepared_statement.h>
#include <cppconn/resultset.h>

#include "CategoryDAOMySQL.hpp"
#include "../MySQLManager.hpp"
#include "../../Entity/DAO/DAOException.hpp"

static const char *ADD_CATEGORY = "INSERT INTO Category (name) VALUES (?)";
static const char *DELETE_CATEGORY = "DELETE FROM Category WHERE id = ?";
static const char *UPDATE_CATEGORY = "UPDATE Category SET name = ? WHERE id = ?";
static const char *CATEGORY_BY_NAME = "SELECT id, name FROM Category WHERE name = ?";
static const char *CATEGORY_BY_ID = "SELECT id, name FROM Category WHERE id = ?";
static const char *ALL_CATEGORIES = "SELECT id, name FROM Category";

void CategoryDAOMySQL::addCategory(Category &category, int &newId)
{
    bool exists = false;

    try
    {
        getCategoryByName(category.getName());
        exists = true;
    }
    catch(DAOException &e)
    {
    }

    if(exists)
    {
        throw DAOException("Category already exists!");
    }

    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(ADD_CATEGORY));

    st->setString(1, category.getName());

    st->execute();

    newId = MySQLManager::lastGeneratedId(ch.conn);
}

void CategoryDAOMySQL::deleteCategory(Category &category)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(DELETE_CATEGORY));

    st->setInt(1, category.getId());

    st->execute();
}

void CategoryDAOMySQL::updateCategory(const Category &category)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(UPDATE_CATEGORY));

    st->setString(1, category.getName());
    st->setInt(2, category.getId());

    st->executeUpdate();
}

Category CategoryDAOMySQL::getCategoryByName(const std::string &name)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(CATEGORY_BY_NAME));

    st->setString(1, name);

    std::unique_ptr<sql::ResultSet> res(st->executeQuery());

    if(!res->next())
    {
        throw DAOException("Category not found.");
    }

    return Category(res->getInt(1), res->getString(2));
}

Category CategoryDAOMySQL::getCategoryById(const int &id)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(CATEGORY_BY_ID));

    st->setInt(1, id);

    std::unique_ptr<sql::ResultSet> res(st->executeQuery());

    if(!res->next())
    {
        throw DAOException("Category not found.");
    }

    return Category(res->getInt(1), res->getString(2));
}

std::vector<Category> CategoryDAOMySQL::getAllCategories()
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(ALL_CATEGORIES));

    std::unique_ptr<sql::ResultSet> res(st->executeQuery());

    std::vector<Category> cats;
    while(res->next())
    {
        cats.push_back(Category(res->getInt(1), res->getString(2)));
    }

    return cats;
}
