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

#ifndef _EDITDAO_HPP_
#define _EDITDAO_HPP_

class EditDAO;

#include "../Edit.hpp"

#include <string>
#include <vector>

class EditDAO
{
public:
    EditDAO() { }
    virtual ~EditDAO() { }

    virtual void addEdit(Edit &edit, int &newId) = 0;
    virtual void deleteEdit(Edit &edit) = 0;
    virtual void updateEdit(const Edit &edit) = 0;
    virtual Edit getEditById(const int &id) = 0;
    virtual std::vector<Edit> getEditByUserId(const int &userId) = 0;
    virtual std::vector<Edit> getEditByFoodId(const int &foodId) = 0;
};

#endif
