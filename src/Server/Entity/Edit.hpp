#ifndef _EDIT_HPP_
#define _EDIT_HPP_

class Edit;

#include <string>

#include "DAO/EditDAO.hpp"

class Edit
{
private:
    int id;
    int userId;
    int foodId;
    std::string editedOn;
    std::string message;

public:
    Edit();
    Edit(const int &id, const int &userId, const int &foodId, const std::string &editedOn, const std::string &message);

    const int &getId() const;
    const int &getUserId() const;
    const int &getFoodId() const;
    const std::string &getEditedOn() const;
    const std::string &getMessage() const;

    void setUserId(const int &userId);
    void setFoodId(const int &foodId);
    void setEditedOn(const std::string &editedOn);
    void setMessage(const std::string &message);

    static Edit makeEdit(const int &userId, const int &foodId, const std::string &editedOn, const std::string &message, EditDAO &dao);
};

#endif
