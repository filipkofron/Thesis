#ifndef _IMAGEDAO_HPP_
#define _IMAGEDAO_HPP_

class ImageDAO;

#include "../Image.hpp"

#include <string>
#include <vector>

class ImageDAO
{
public:
    ImageDAO() { }
    virtual ~ImageDAO() { }

    virtual void addImage(Image &image, int &newId) = 0;
    virtual void deleteImage(Image &image) = 0;
    virtual void updateImage(const Image &image) = 0;
    virtual Image getImageById(const int &id) = 0;
    virtual std::vector<Image> getImageByUserId(const int &userId) = 0;
    virtual std::vector<Image> getImageByFoodId(const int &foodId) = 0;
};

#endif
