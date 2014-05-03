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

#ifndef _IMAGE_HPP_
#define _IMAGE_HPP_

#include <string>

class Image;

#include "DAO/ImageDAO.hpp"

class Image
{
private:
    int id;
    std::string description;
    int foodId;
    int userId;
public:
    Image();
    Image(const int &id, const std::string &description, const int &foodId, const int &userId);

    const int &getId() const;
    const std::string &getDescription() const;
    const int &getFoodId() const;
    const int &getUserId() const;

    void setDescription(const std::string &description);
    void setFoodId(const int &foodId);
    void setUserId(const int &userId);

    static Image makeImage(const std::string &description, const int &foodId, const int &userId, ImageDAO &dao);
};

#endif
