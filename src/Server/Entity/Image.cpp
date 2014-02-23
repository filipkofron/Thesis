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
