#ifndef _EDITDAO_HPP_
#define _EDITDAO_HPP_

class EditDAO;

#include "../Edit.hpp"

#include <string>
#include <vector>

class EditDAO
{
public:
    EditDAO() { }
    virtual ~EditDAO() { }

    virtual void addEdit(Edit &edit, int &newId) = 0;
    virtual void deleteEdit(Edit &edit) = 0;
    virtual void updateEdit(const Edit &edit) = 0;
    virtual Edit getEditById(const int &id) = 0;
    virtual std::vector<Edit> getEditByUserId(const int &userId) = 0;
    virtual std::vector<Edit> getEditByFoodId(const int &foodId) = 0;
};

#endif
