#include "EditDAOMySQL.hpp"

#include "../MySQLManager.hpp"
#include "../../Entity/DAO/DAOException.hpp"

#include <cppconn/prepared_statement.h>
#include <cppconn/resultset.h>

static const char *ADD_EDIT = "INSERT INTO Edit (user_id, food_id, edit_on, message) VALUES (?, ?, ?, ?)";
static const char *DELETE_EDIT = "DELETE FROM Edit WHERE id = ?";
static const char *UPDATE_EDIT = "UPDATE Edit SET user_id = ?, food_id = ?, edit_on = ?, message = ? WHERE id = ?";
static const char *EDIT_BY_ID = "SELECT id, user_id, food_id, edit_on, message FROM Edit WHERE id = ?";
static const char *EDITS_BY_USERID = "SELECT id, user_id, food_id, edit_on, message FROM Edit WHERE user_id = ?";
static const char *EDITS_BY_FOODID = "SELECT id, user_id, food_id, edit_on, message FROM Edit WHERE food_id = ?";

void EditDAOMySQL::addEdit(Edit &edit, int &newId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(ADD_EDIT));

    ps->setInt(1, edit.getUserId());
    ps->setInt(2, edit.getFoodId());
    ps->setString(3, edit.getEditedOn());
    ps->setString(4, edit.getMessage());

    ps->execute();

    newId = MySQLManager::lastGeneratedId(ch.conn);
}

void EditDAOMySQL::deleteEdit(Edit &edit)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(DELETE_EDIT));

    ps->setInt(1, edit.getId());

    ps->execute();
}

void EditDAOMySQL::updateEdit(const Edit &edit)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(UPDATE_EDIT));

    ps->setInt(1, edit.getUserId());
    ps->setInt(2, edit.getFoodId());
    ps->setString(3, edit.getEditedOn());
    ps->setString(4, edit.getMessage());
    ps->setInt(5, edit.getId());

    ps->executeUpdate();
}

Edit EditDAOMySQL::getEditById(const int &id)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(EDIT_BY_ID));

    ps->setInt(1, id);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    if(!res->next())
    {
        throw DAOException("Edit not found!");
    }

    return Edit(res->getInt(1), res->getInt(2), res->getInt(3), res->getString(4), res->getString(5));
}

std::vector<Edit> EditDAOMySQL::getEditByUserId(const int &userId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(EDITS_BY_USERID));

    ps->setInt(1, userId);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Edit> edits;
    while(res->next())
    {
        edits.push_back(Edit(res->getInt(1), res->getInt(2), res->getInt(3), res->getString(4), res->getString(5)));
    }

    return edits;
}

std::vector<Edit> EditDAOMySQL::getEditByFoodId(const int &foodId)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(EDITS_BY_FOODID));

    ps->setInt(1, foodId);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Edit> edits;
    while(res->next())
    {
        edits.push_back(Edit(res->getInt(1), res->getInt(2), res->getInt(3), res->getString(4), res->getString(5)));
    }

    return edits;
}
