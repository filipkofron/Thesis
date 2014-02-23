#ifndef _USERDAOMYSQL_HPP_
#define _USERDAOMYSQL_HPP_

class UserDAOMySQL;

#include "../../Entity/DAO/UserDAO.hpp"

class UserDAOMySQL : public UserDAO
{
public:
    UserDAOMySQL() { }
    virtual ~UserDAOMySQL() { }

    virtual void addUser(User &user, int &newId);
    virtual void deleteUser(User &user);
    virtual void updateUser(const User &user);
    virtual User getUserByUsername(const std::string &username);
    virtual User getUserById(const int &id);
    virtual std::vector<User> getAllUsers();
};

#endif
