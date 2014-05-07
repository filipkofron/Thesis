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

#ifndef _CATEGORYDAO_HPP_
#define _CATEGORYDAO_HPP_

class CategoryDAO;

#include <string>
#include <vector>

#include "../Category.hpp"

class CategoryDAO
{
public:
    CategoryDAO() { }
    virtual ~CategoryDAO() { }

    virtual void addCategory(Category &category, int &newId) = 0;
    virtual void deleteCategory(Category &category) = 0;
    virtual void updateCategory(const Category &category) = 0;
    virtual Category getCategoryByName(const std::string &name) = 0;
    virtual Category getCategoryById(const int &id) = 0;
    virtual std::vector<Category> getAllCategories() = 0;
};

#endif
