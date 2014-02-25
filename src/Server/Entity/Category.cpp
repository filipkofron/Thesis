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
