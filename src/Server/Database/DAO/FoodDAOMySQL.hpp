#ifndef _FOODDAOMYSQL_HPP_
#define _FOODDAOMYSQL_HPP_

class FoodDAOMySQL;

#include "../../Entity/DAO/FoodDAO.hpp"

class FoodDAOMySQL
{
public:
    FoodDAOMySQL() { }
    virtual ~FoodDAOMySQL() { }

    virtual void addFood(Food &food, int &newId);
    virtual void deleteFood(Food &food);
    virtual void updateFood(const Food &food);
    virtual Food getFoodById(const int &id);
    virtual Food getFoodByGtin(const std::string &gtin);
    virtual std::vector<Food> findFoodByGtin(const std::string &gtin);
    virtual std::vector<Food> findFoodByName(const std::string &name);
    virtual std::vector<Food> getFoodByUserId(const int &userId);
    virtual std::vector<Food> getFoodByCategoryId(const int &categoryId);
    virtual std::vector<Food> getFoodByVendorId(const int &vendorId);
    virtual std::vector<Food> getAllFood(const int &offset, const int &max);
};

#endif
