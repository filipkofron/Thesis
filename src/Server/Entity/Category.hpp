#ifndef _CATEGORY_HPP_
#define _CATEGORY_HPP_

class Category;

#include "DAO/CategoryDAO.hpp"

#include <string>

class Category
{
private:
    int id;
    std::string name;
public:
    Category();
    Category(const int &id, const std::string &name);

    const int &getId() const;
    const std::string &getName() const;

    void setName(const std::string &name);

    static Category makeCategory(const std::string &name, CategoryDAO &dao);
};

#endif
