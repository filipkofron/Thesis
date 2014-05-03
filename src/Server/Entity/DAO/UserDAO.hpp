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

#ifndef _USERDAO_HPP_
#define _USERDAO_HPP_

class UserDAO;

#include "../User.hpp"

#include <string>
#include <vector>

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
    virtual std::vector<User> getAllUsers() = 0;
};

#endif
