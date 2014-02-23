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
