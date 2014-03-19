#ifndef _USER_HPP_
#define _USER_HPP_

class User;

#include <string>
#include "DAO/UserDAO.hpp"

#define USER_SALT_BYTE_LEN 16

class User
{
private:
    int id;
    std::string username;
public:
    User();
    User(const int &id, const std::string &username);

    const std::string &getUserName() const;
    const int &getId() const;

    void setUserName(const std::string &username);

    static User makeUser(const std::string &username, UserDAO &userDAO);
};

#endif
