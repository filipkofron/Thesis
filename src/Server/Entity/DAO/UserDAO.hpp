#ifndef _USERDAO_HPP_
#define _USERDAO_HPP_

class UserDAO;

#include "../User.hpp"

#include <string>

class UserDAO
{
public:
    UserDAO() {}
    virtual ~UserDAO() {}

    virtual void addUser(User &user, int &newId) = 0;
    virtual void deleteUser(User &user) = 0;
    virtual void updateUser(const User &user) = 0;
    virtual User getUserByUsername(const std::string &username) = 0;
    virtual User getUserById(const int &id) = 0;
};

#endif
