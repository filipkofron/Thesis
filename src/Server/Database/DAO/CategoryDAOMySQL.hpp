#ifndef _CATEGORYDAOMYSQL_HPP_
#define _CATEGORYDAOMYSQL_HPP_

class CategoryDAOMySQL;

#include "../../Entity/DAO/CategoryDAO.hpp"

class CategoryDAOMySQL : public CategoryDAO
{
public:
    CategoryDAOMySQL() { }
    virtual ~CategoryDAOMySQL() { }

    virtual void addCategory(Category &category, int &newId);
    virtual void deleteCategory(Category &category);
    virtual void updateCategory(const Category &category);
    virtual Category getCategoryByName(const std::string &name);
    virtual Category getCategoryById(const int &id);
    virtual std::vector<Category> getAllCategories();
};

#endif
