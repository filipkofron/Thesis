#include "GetFoodBaseHandler.hpp"
#include "../Protocol/GetFoodBaseResponse.hpp"
#include "../Network/MessageSender.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include <vector>

GetFoodBaseHandler::GetFoodBaseHandler(GetFoodBaseRequest *request)
    : request(request)
{

}

GetFoodBaseHandler::~GetFoodBaseHandler()
{

}

void GetFoodBaseHandler::handle(Context &context)
{
    std::shared_ptr<VendorDAO> vendorDao = DAOFactory::getVendorDAO();
    std::shared_ptr<CategoryDAO> categoryDAO = DAOFactory::getCategoryDAO();

    std::vector<Vendor> vendors = vendorDao->getAllVendors();
    std::vector<Category> categories = categoryDAO->getAllCategories();

    Json::Value vendorArray;
    Json::Value categoryArray;

    int i = 0;
    for(Vendor &vendor : vendors)
    {
        Json::Value obj;
        obj["id"] = vendor.getId();
        obj["name"] = vendor.getName();
        vendorArray[i] = obj;
        i++;
    }

    i = 0;
    for(Category &category : categories)
    {
        Json::Value obj;
        obj["id"] = category.getId();
        obj["name"] = category.getName();
        categoryArray[i] = obj;
        i++;
    }

    GetFoodBaseResponse response(categoryArray, vendorArray);
    MessageSender::sendMessage(context, response);
}
