/*
Copyright (C) 2014 Filip Kofron (filip.kofron.cz@gmail.com)

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

#ifndef _FOOD_HPP_
#define _FOOD_HPP_

class Food;

#include <string>

#include "DAO/FoodDAO.hpp"

#define MAX_GTIN_LENGTH 14
#define AMOUNT_TYPE_GRAMS 0
#define AMOUNT_TYPE_LITRES 1
#define AMOUNT_TYPE_PIECES 2

class Food
{
private:
    int id;
    std::string gtin;
    std::string name;
    std::string description;
    int categoryId;
    int64_t defaultUseBy;
    int amountMeasure;
    float amount;
    int userId;
    float price;
    int vendorId;

public:
    Food();
    Food(const int &id,
         const std::string &gtin,
         const std::string &name,
         const std::string &description,
         const int &categoryId,
         const int64_t &defaultUseBy,
         const int &amountMeasure,
         const float &amount,
         const int &userId,
         const float &price,
         const int &vendorId);

    const int &getId() const;
    const std::string &getGtin() const;
    const std::string &getName() const;
    const std::string &getDescription() const;
    const int &getCategoryId() const;
    const int64_t &getDefaultUseBy() const;
    const int &getAmountMeasure() const;
    const float &getAmount() const;
    const int &getUserId() const;
    const float &getPrice() const;
    const int &getVendorId() const;

    void setGtin(const std::string &gtin);
    void setName(const std::string &name);
    void setDescription(const std::string &description);
    void setCategoryId(const int &categoryId);
    void setDefaultUseBy(const int64_t &defaultUseBy);
    void setAmountMeasure(const int &amountMeasure);
    void setAmount(const float &amount);
    void setUserId(const int &userId);
    void setPrice(const float &price);
    void setVendorId(const int &vendorId);

    static std::string fixGtin(const std::string &plain);

    static Food makeFood(const std::string &gtin,
                          const std::string &name,
                          const std::string &description,
                          const int &categoryId,
                          const int64_t &defaultUseBy,
                          const int &amountMeasure,
                          const float &amount,
                          const int &userId,
                          const float &price,
                          const int &vendorId,
                          FoodDAO &dao);
};

#endif
