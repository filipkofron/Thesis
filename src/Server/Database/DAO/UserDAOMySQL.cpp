#include "UserDAOMySQL.hpp"
#include "../../Entity/DAO/DAOException.hpp"

#include "../MySQLManager.hpp"

#include <memory>
#include <cppconn/prepared_statement.h>

static const char *ADD_USER = "INSERT INTO User (username, password, salt) VALUES (?, ?, ?)";
static const char *DELETE_USER = "DELETE FROM User WHERE id = ?";
static const char *UPDATE_USER = "UPDATE User SET username = ?, password = ?, salt = ? WHERE id = ?";
static const char *USER_BY_USERNAME = "SELECT id, username, password, salt FROM User WHERE username = ?";
static const char *USER_BY_ID = "SELECT id, username, password, salt FROM User WHERE id = ?";
static const char *ALL_USERS = "SELECT id, username, password, salt FROM User";

void UserDAOMySQL::addUser(User &user, int &newId)
{
    bool exists = false;
    try
    {
        getUserByUsername(user.getUserName());
        exists = true;
    }
    catch(DAOException &e)
    {
    }

    if(exists)
    {
        throw DAOException("User alread exists!");
    }

    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(ADD_USER));

    st->setString(1, user.getUserName());
    st->setString(2, user.getPassword());
    st->setString(3, user.getSalt());

    st->execute();

    newId = MySQLManager::lastGeneratedId(ch.conn);
}

void UserDAOMySQL::deleteUser(User &user)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(DELETE_USER));

    st->setInt(1, user.getId());

    st->execute();
}

void UserDAOMySQL::updateUser(const User &user)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(UPDATE_USER));

    st->setString(1, user.getUserName());
    st->setString(2, user.getPassword());
    st->setString(3, user.getSalt());
    st->setInt(4, user.getId());

    st->executeUpdate();
}

User UserDAOMySQL::getUserByUsername(const std::string &username)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(USER_BY_USERNAME));

    st->setString(1, username);
    std::unique_ptr<sql::ResultSet> res(st->executeQuery());

    if(!res->next())
    {
        throw DAOException("User not found.");
    }

    return User(res->getInt(1), res->getString(2), res->getString(3), res->getString(4));
}

User UserDAOMySQL::getUserById(const int &id)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(USER_BY_ID));

    st->setInt(1, id);
    std::unique_ptr<sql::ResultSet> res(st->executeQuery());

    if(!res->next())
    {
        throw DAOException("User not found.");
    }

    return User(res->getInt(1), res->getString(2), res->getString(3), res->getString(4));
}

std::vector<User> UserDAOMySQL::getAllUsers()
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> st(ch.conn->prepareStatement(ALL_USERS));

    std::unique_ptr<sql::ResultSet> res(st->executeQuery());
    std::vector<User> users;
    while(res->next())
    {
        users.push_back(User(res->getInt(1), res->getString(2), res->getString(3), res->getString(4)));
    }

    return users;
}
