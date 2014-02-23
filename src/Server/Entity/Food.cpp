#include "Food.hpp"

Food::Food()
{

}

Food::Food(const int &id,
     const std::string &gtin,
     const std::string &name,
     const std::string &description,
     const int &categoryId,
     const long &defaultUseBy,
     const int &amountMeasure,
     const float &amount,
     const int &userId,
     const float &price,
     const int &vendorId)
    : id(id), gtin(gtin), name(name), description(description), categoryId(categoryId), defaultUseBy(defaultUseBy),
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

const long &Food::getDefaultUseBy() const
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

const float &Food::getPrice()
{
    return price;
}

const int &Food::getVendorId()
{
    return vendorId;
}

void Food::setGtin(const std::string &gtin)
{
    this->gtin = gtin;
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

void Food::setDefaultUseBy(const long &defaultUseBy)
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
