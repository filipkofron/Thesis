#include "UserDAOImpl.hpp"
#include <map>
#include <utility>
#include "DAOException.hpp"

static std::map<int, User *> users;

static int generateId()
{
    static int ids = 0;

    return ids++;
}

void UserDAOImpl::addUser(User &user, int &newId)
{
    bool found = false;
    try
    {
        getUserByUsername(user.getUserName());
        found = true;
    }
    catch(DAOException &e)
    {
        found = false;
    }

    if(found)
    {
        throw DAOException("Username alread exists!");
    }

    newId = generateId();
    users.insert(std::make_pair(newId, new User(user)));
}

void UserDAOImpl::deleteUser(User &user)
{
    auto it = users.find(user.getId());
    if(it == users.end())
    {
        throw DAOException("User not found.");
    }

    users.erase(it);
}

void UserDAOImpl::updateUser(const User &user)
{
    auto it = users.find(user.getId());
    if(it == users.end())
    {
        throw DAOException("User not found.");
    }

    *it->second = user;
}

User UserDAOImpl::getUserByUsername(const std::string &username)
{
    for(auto &item : users)
    {
        if(username.compare(item.second->getUserName()) == 0)
        {
            return *item.second;
        }
    }
    throw DAOException("Username not found.");
}

User UserDAOImpl::getUserById(const int &id)
{
    auto it = users.find(id);
    if(it == users.end())
    {
        throw DAOException("User ID not found.");
    }

    return *it->second;
}
