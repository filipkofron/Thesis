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

#include "Edit.hpp"

Edit::Edit()
{

}

Edit::Edit(const int &id, const int & userId, const int &foodId, const std::string &editedOn, const std::string &message)
    : id(id), userId(userId), foodId(foodId), editedOn(editedOn), message(message)
{

}

const int &Edit::getId() const
{
    return id;
}

const int &Edit::getUserId() const
{
    return userId;
}

const int &Edit::getFoodId() const
{
    return foodId;
}

const std::string &Edit::getEditedOn() const
{
    return editedOn;
}

const std::string &Edit::getMessage() const
{
    return message;
}

void Edit::setUserId(const int &userId)
{
    this->userId = userId;
}

void Edit::setFoodId(const int &foodId)
{
    this->foodId = foodId;
}

void Edit::setEditedOn(const std::string &editedOn)
{
    this->editedOn = editedOn;
}

void Edit::setMessage(const std::string &message)
{
    this->message = message;
}

Edit Edit::makeEdit(const int &userId, const int &foodId, const std::string &editedOn, const std::string &message, EditDAO &dao)
{
    Edit edit(0, userId, foodId, editedOn, message);

    int newId = 0;

    dao.addEdit(edit, newId);

    edit.id = newId;

    return edit;
}
