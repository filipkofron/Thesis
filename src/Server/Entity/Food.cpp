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

#include "Food.hpp"

static const char *prefixes[] =
{
    "Z", // 0
    "Z", // 1
    "Z", // 2
    "Z", // 3
    "Z", // 4
    "Z", // 5
    "Z", // 6
    "Z", // 7
    "A", // 8
    "Z", // 9
    "Z", // 10
    "Z", // 11
    "B", // 12
    "C", // 13
    "D", // 14
};

Food::Food()
{

}

Food::Food(const int &id,
     const std::string &gtin,
     const std::string &name,
     const std::string &description,
     const int &categoryId,
     const int64_t &defaultUseBy,
     const int &amountMeasure,
     const float &amount,
     const int &userId,
     const float &price,
     const int &vendorId)
    : id(id), gtin(fixGtin(gtin)), name(name), description(description), categoryId(categoryId), defaultUseBy(defaultUseBy),
      amountMeasure(amountMeasure), amount(amount), userId(userId), price(price), vendorId(vendorId)
{

}

const int &Food::getId() const
{
    return id;
}

const std::string &Food::getGtin() const
{
    return gtin;
}

const std::string &Food::getName() const
{
    return name;
}

const std::string &Food::getDescription() const
{
    return description;
}

const int &Food::getCategoryId() const
{
    return categoryId;
}

const int64_t &Food::getDefaultUseBy() const
{
    return defaultUseBy;
}

const int &Food::getAmountMeasure() const
{
    return amountMeasure;
}

const float &Food::getAmount() const
{
    return amount;
}

const int &Food::getUserId() const
{
    return userId;
}

const float &Food::getPrice() const
{
    return price;
}

const int &Food::getVendorId() const
{
    return vendorId;
}

void Food::setGtin(const std::string &gtin)
{
    this->gtin = fixGtin(gtin);
}

void Food::setName(const std::string &name)
{
    this->name = name;
}

void Food::setDescription(const std::string &description)
{
    this->description = description;
}

void Food::setCategoryId(const int &categoryId)
{
    this->categoryId = categoryId;
}

void Food::setDefaultUseBy(const int64_t &defaultUseBy)
{
    this->defaultUseBy = defaultUseBy;
}

void Food::setAmountMeasure(const int &amountMeasure)
{
    this->amountMeasure = amountMeasure;
}

void Food::setAmount(const float &amount)
{
    this->amount = amount;
}

void Food::setUserId(const int &userId)
{
    this->userId = userId;
}

void Food::setPrice(const float &price)
{
    this->price = price;
}

void Food::setVendorId(const int &vendorId)
{
    this->vendorId = vendorId;
}

std::string Food::fixGtin(const std::string &plain)
{
    std::string temp("");
    int len = plain.length();

    if(len >= 15)
    {
        /*
         * Already fixed or fail value!
         */
        return plain;
    }

    /*
     * Assign a prefix:
     * "A" - GTIN 8
     * "B" - GTIN 12
     * "C" - GTIN 13
     * "D" - GTIN 14
     */
    const char *prefix = prefixes[len];

    temp.append(prefix);

    for(int i = 0; i < MAX_GTIN_LENGTH - len; i++)
    {
        temp.append("0");
    }

    temp.append(plain);

    return temp;
}

Food Food::makeFood(const std::string &gtin,
                          const std::string &name,
                          const std::string &description,
                          const int &categoryId,
                          const int64_t &defaultUseBy,
                          const int &amountMeasure,
                          const float &amount,
                          const int &userId,
                          const float &price,
                          const int &vendorId,
                          FoodDAO &dao)
{
    Food food(0, gtin, name, description, categoryId, defaultUseBy, amountMeasure, amount, userId, price, vendorId);

    int newId = 0;

    dao.addFood(food, newId);

    food.id = newId;

    return food;
}
