#ifndef _IMAGE_HPP_
#define _IMAGE_HPP_

#include <string>

class Image;

#include "DAO/ImageDAO.hpp"

class Image
{
private:
    int id;
    std::string description;
    int foodId;
    int userId;
public:
    Image();
    Image(const int &id, const std::string &description, const int &foodId, const int &userId);

    const int &getId() const;
    const std::string &getDescription() const;
    const int &getFoodId() const;
    const int &getUserId() const;

    void setDescription(const std::string &description);
    void setFoodId(const int &foodId);
    void setUserId(const int &userId);

    static Image makeImage(const std::string &description, const int &foodId, const int &userId, ImageDAO &dao);
};

#endif
