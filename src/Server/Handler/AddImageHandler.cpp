#include "AddImageHandler.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Protocol/AddImageResponse.hpp"
#include "../Network/MessageSender.hpp"
#include "../Config/Configurator.hpp"
#include "../Util/Date.hpp"
#include "../Util/Base64.hpp"
#include <stdio.h>
#include <iostream>

AddImageHandler::AddImageHandler(AddImageRequest *request)
    : request(request)
{

}

AddImageHandler::~AddImageHandler()
{

}

void AddImageHandler::handle(Context &context)
{
    if(!context.getLoggedIn())
    {
        AddImageResponse response(false);
        MessageSender::sendMessage(context, response);
    }

    std::shared_ptr<EditDAO> editDao = DAOFactory::getEditDAO();
    std::shared_ptr<ImageDAO> imageDao = DAOFactory::getImageDAO();
    std::shared_ptr<FoodDAO> foodDao = DAOFactory::getFoodDAO();

    Food food = foodDao->getFoodById(request->foodId);

    Image image = Image::makeImage("", food.getId(), context.getUserId(), *imageDao);
    Edit edit = Edit::makeEdit(context.getUserId(), food.getId(), Date::unixTimeToMysqlString(Date::currentTimeMilis()), "User added image.", *editDao);


    std::string path = Configurator::getInstance()->getImageDir() + "/" + std::to_string(image.getId()) + ".jpg";

    std::cout << "Decoding: '" << request->image << "'" << std::endl;
    std::vector<uint8_t> bytes = Base64::decode(request->image);
    FILE *file = fopen(path.c_str(), "wb+");

    bool res = file != NULL;

    for(uint8_t &byte : bytes)
    {
        fputc(byte, file);
    }

    fclose(file);

    AddImageResponse response(res);
    MessageSender::sendMessage(context, response);
}
