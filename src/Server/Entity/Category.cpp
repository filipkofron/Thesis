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
