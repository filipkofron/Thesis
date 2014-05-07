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
