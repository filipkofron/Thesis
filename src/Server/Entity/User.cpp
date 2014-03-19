#include "User.hpp"
#include "../Util/SHA256.hpp"
#include "../Util/Random.hpp"

User::User()
{
}

User::User(const int &id, const std::string &username)
    : id(id), username(username)
{
}

const std::string &User::getUserName() const
{
    return username;
}


const int &User::getId() const
{
    return id;
}

void User::setUserName(const std::string &username)
{
    this->username = username;
}

User User::makeUser(const std::string &username, UserDAO &userDAO)
{
    User user(0, username);

    int newId = 0;
    userDAO.addUser(user, newId);

    user.id = newId;

    return user;
}
