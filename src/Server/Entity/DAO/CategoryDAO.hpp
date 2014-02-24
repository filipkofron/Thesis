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
