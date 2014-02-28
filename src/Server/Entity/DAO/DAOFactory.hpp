#ifndef _DAOFACTORY_HPP_
#define _DAOFACTORY_HPP_

class DAOFactory;

#include "CategoryDAO.hpp"
#include "EditDAO.hpp"
#include "FoodDAO.hpp"
#include "ImageDAO.hpp"
#include "InventoryDAO.hpp"
#include "ReviewDAO.hpp"
#include "UserDAO.hpp"
#include "VendorDAO.hpp"

#include <memory>

class DAOFactory
{
private:
    static std::shared_ptr<CategoryDAO> categoryDAO;
    static std::shared_ptr<EditDAO> editDAO;
    static std::shared_ptr<FoodDAO> foodDAO;
    static std::shared_ptr<ImageDAO> imageDAO;
    static std::shared_ptr<InventoryDAO> inventoryDAO;
    static std::shared_ptr<ReviewDAO> reviewDAO;
    static std::shared_ptr<UserDAO> userDAO;
    static std::shared_ptr<VendorDAO> vendorDAO;

public:
    static std::shared_ptr<CategoryDAO> getCategoryDAO();
    static std::shared_ptr<EditDAO> getEditDAO();
    static std::shared_ptr<FoodDAO> getFoodDAO();
    static std::shared_ptr<ImageDAO> getImageDAO();
    static std::shared_ptr<InventoryDAO> getInventoryDAO();
    static std::shared_ptr<ReviewDAO> getReviewDAO();
    static std::shared_ptr<UserDAO> getUserDAO();
    static std::shared_ptr<VendorDAO> getVendorDAO();
};

#endif
