#include "User.hpp"
#include "../Util/SHA256.hpp"
#include "../Util/Random.hpp"

User::User(const int &id, const std::string &username, const std::string &password, const std::string &salt)
    : id(id), username(username), password(password), salt(salt)
{
}

const std::string &User::getUserName() const
{
    return username;
}

const std::string &User::getPassword() const
{
    return password;
}

const std::string &User::getSalt() const
{
    return salt;
}

const int &User::getId() const
{
    return id;
}

void User::setUserName(const std::string &username)
{
    this->username = username;
}

void User::setPassword(const std::string &plainPassword)
{
    this->password = calculateHash(username, plainPassword, salt);
}

void User::setSalt(const std::string &salt)
{
    this->salt = salt;
}

std::string User::calculateHash(const std::string &username, const std::string &plainPassword, const std::string &salt)
{
    std::string all = salt;
    all.append(salt);
    all.append(username);
    all.append(salt);
    all.append(plainPassword);
    all.append(salt);

    return sha256(all);
}

User User::makeUser(const std::string &username, const std::string &plainPassword, UserDAO &userDAO)
{
    std::string salt = generateRandomHexaString(USER_SALT_BYTE_LEN);

    User user(0, username, calculateHash(username, plainPassword, salt), salt);

    int newId = 0;
    userDAO.addUser(user, newId);

    user.id = newId;

    return user;
}

bool User::checkPassword(const std::string &plainPassword) const
{
    return password.compare(calculateHash(username, plainPassword, salt)) == 0;
}
