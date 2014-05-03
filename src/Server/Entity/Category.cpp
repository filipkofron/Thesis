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

#include "Category.hpp"

Category::Category()
{

}

Category::Category(const int &id, const std::string &name)
    : id(id), name(name)
{

}

const int &Category::getId() const
{
    return id;
}

const std::string &Category::getName() const
{
    return name;
}

void Category::setName(const std::string &name)\
{
    this->name = name;
}

Category Category::makeCategory(const std::string &name, CategoryDAO &dao)
{
    Category category(0, name);

    int newId = 0;

    dao.addCategory(category, newId);

    category.id = newId;

    return category;
}
