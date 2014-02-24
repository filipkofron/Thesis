#ifndef _IMAGEDAOMYSQL_HPP_
#define _IMAGEDAOMYSQL_HPP_

class ImageDAOMySQL;

#include "../../Entity/DAO/ImageDAO.hpp"

class ImageDAOMySQL : public ImageDAO
{
public:
    ImageDAOMySQL() { }
    virtual ~ImageDAOMySQL() { }

    virtual void addImage(Image &image, int &newId);
    virtual void deleteImage(Image &image);
    virtual void updateImage(const Image &image);
    virtual Image getImageById(const int &id);
    virtual std::vector<Image> getImageByUserId(const int &userId);
    virtual std::vector<Image> getImageByFoodId(const int &foodId);
};

#endif
