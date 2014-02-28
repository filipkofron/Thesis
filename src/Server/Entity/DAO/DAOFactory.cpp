#include "DAOFactory.hpp"

#include "../../Database/DAO/CategoryDAOMySQL.hpp"
#include "../../Database/DAO/EditDAOMySQL.hpp"
#include "../../Database/DAO/FoodDAOMySQL.hpp"
#include "../../Database/DAO/ImageDAOMySQL.hpp"
#include "../../Database/DAO/InventoryDAOMySQL.hpp"
#include "../../Database/DAO/ReviewDAOMySQL.hpp"
#include "../../Database/DAO/UserDAOMySQL.hpp"
#include "../../Database/DAO/VendorDAOMySQL.hpp"

std::shared_ptr<CategoryDAO> DAOFactory::categoryDAO(new CategoryDAOMySQL());
std::shared_ptr<EditDAO> DAOFactory::editDAO(new EditDAOMySQL());
std::shared_ptr<FoodDAO> DAOFactory::foodDAO(new FoodDAOMySQL());
std::shared_ptr<ImageDAO> DAOFactory::imageDAO(new ImageDAOMySQL());
std::shared_ptr<InventoryDAO> DAOFactory::inventoryDAO(new InventoryDAOMySQL());
std::shared_ptr<ReviewDAO> DAOFactory::reviewDAO(new ReviewDAOMySQL());
std::shared_ptr<UserDAO> DAOFactory::userDAO(new UserDAOMySQL());
std::shared_ptr<VendorDAO> DAOFactory::vendorDAO(new VendorDAOMySQL());

std::shared_ptr<CategoryDAO> DAOFactory::getCategoryDAO()
{
    return categoryDAO;
}

std::shared_ptr<EditDAO> DAOFactory::getEditDAO()
{
    return editDAO;
}

std::shared_ptr<FoodDAO> DAOFactory::getFoodDAO()
{
    return foodDAO;
}

std::shared_ptr<ImageDAO> DAOFactory::getImageDAO()
{
    return imageDAO;
}

std::shared_ptr<InventoryDAO> DAOFactory::getInventoryDAO()
{
    return inventoryDAO;
}

std::shared_ptr<ReviewDAO> DAOFactory::getReviewDAO()
{
    return reviewDAO;
}

std::shared_ptr<UserDAO> DAOFactory::getUserDAO()
{
    return userDAO;
}

std::shared_ptr<VendorDAO> DAOFactory::getVendorDAO()
{
    return vendorDAO;
}
