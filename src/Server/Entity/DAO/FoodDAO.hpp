#ifndef _FOODDAO_HPP_
#define _FOODDAO_HPP_

class FoodDAO;

#include "../Food.hpp"

#include <string>
#include <vector>

class FoodDAO
{
public:
    FoodDAO() { }
    virtual ~FoodDAO() { }

    virtual void addFood(Food &food, int &newId) = 0;
    virtual void deleteFood(Food &food) = 0;
    virtual void updateFood(const Food &food) = 0;
    virtual Food getFoodById(const int &id) = 0;
    virtual Food getFoodByGtin(const std::string &gtin) = 0;
    virtual std::vector<Food> findFoodByGtin(const std::string &gtin) = 0;
    virtual std::vector<Food> findFoodByName(const std::string &name) = 0;
    virtual std::vector<Food> getFoodByUserId(const int &userId) = 0;
    virtual std::vector<Food> getFoodByCategoryId(const int &categoryId) = 0;
    virtual std::vector<Food> getFoodByVendorId(const int &vendorId) = 0;
    virtual std::vector<Food> getAllFood() = 0;
};

#endif
