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
    std::string password;
    std::string salt;
public:
    User();
    User(const int &id, const std::string &username, const std::string &password, const std::string &salt);

    const std::string &getUserName() const;
    const std::string &getPassword() const;
    const std::string &getSalt() const;
    const int &getId() const;

    static std::string calculateHash(const std::string &username, const std::string &plainPassword, const std::string &salt);

    void setUserName(const std::string &username);
    void setPassword(const std::string &plainPassword);
    void setSalt(const std::string &salt);

    static User makeUser(const std::string &username, const std::string &password, UserDAO &userDAO);

    bool checkPassword(const std::string &plainPassword) const;
};

#endif
