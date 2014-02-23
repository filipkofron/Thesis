#ifndef _FOOD_HPP_
#define _FOOD_HPP_

#include <string>

class Food
{
private:
    int id;
    std::string gtin;
    std::string name;
    std::string description;
    int categoryId;
    long defaultUseBy;
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
         const long &defaultUseBy,
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
    const long &getDefaultUseBy() const;
    const int &getAmountMeasure() const;
    const float &getAmount() const;
    const int &getUserId() const;
    const float &getPrice();
    const int &getVendorId();

    void setGtin(const std::string &gtin);
    void setName(const std::string &name);
    void setDescription(const std::string &description);
    void setCategoryId(const int &categoryId);
    void setDefaultUseBy(const long &defaultUseBy);
    void setAmountMeasure(const int &amountMeasure);
    void setAmount(const float &amount);
    void setUserId(const int &userId);
    void setPrice(const float &price);
    void setVendorId(const int &vendorId);
};

#endif
