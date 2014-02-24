#ifndef _EDITDAOMYSQL_HPP_
#define _EDITDAOMYSQL_HPP_

class EditDAOMySQL;

#include "../../Entity/DAO/EditDAO.hpp"

class EditDAOMySQL
{
public:
    EditDAOMySQL() { }
    virtual ~EditDAOMySQL() { }

    virtual void addEdit(Edit &edit, int &newId);
    virtual void deleteEdit(Edit &edit);
    virtual void updateEdit(const Edit &edit);
    virtual Edit getEditById(const int &id);
    virtual std::vector<Edit> getEditByUserId(const int &userId);
    virtual std::vector<Edit> getEditByFoodId(const int &foodId);
};

#endif
