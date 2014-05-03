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

#include "Image.hpp"

Image::Image()
{

}

Image::Image(const int &id, const std::string &description, const int &foodId, const int &userId)
    : id(id), description(description), foodId(foodId), userId(userId)
{

}

const int &Image::getId() const
{
    return id;
}

const std::string &Image::getDescription() const
{
    return description;
}

const int &Image::getFoodId() const
{
    return foodId;
}

const int &Image::getUserId() const
{
    return userId;
}

void Image::setDescription(const std::string &description)
{
    this->description = description;
}

void Image::setFoodId(const int &foodId)
{
    this->foodId = foodId;
}

void Image::setUserId(const int &userId)
{
    this->userId = userId;
}

Image Image::makeImage(const std::string &description, const int &foodId, const int &userId, ImageDAO &dao)
{
    Image image(0, description, foodId, userId);

    int newId = 0;

    dao.addImage(image, newId);
    image.id = newId;

    return image;
}
