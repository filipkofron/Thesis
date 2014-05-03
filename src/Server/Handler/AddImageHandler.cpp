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

#include "AddImageHandler.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Protocol/AddImageResponse.hpp"
#include "../Network/MessageSender.hpp"
#include "../Config/Configurator.hpp"
#include "../Util/Date.hpp"
#include "../Util/Base64.hpp"
#include <stdio.h>

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
    Edit edit = Edit::makeEdit(context.getUserId(), food.getId(), Date::unixTimeToMysqlString(Date::currentTimeMilis() / 1000), "User added image.", *editDao);

    std::string path = Configurator::getInstance()->getImageDir() + "/" + std::to_string(image.getId()) + ".jpg";

    const char *encoded = request->image.c_str();
    int size = Base64decode_len(encoded) + 1;
    char *decoded = (char *) calloc(size * sizeof(char), sizeof(char));

    Base64decode(decoded, encoded);
    FILE *file = fopen(path.c_str(), "wb+");

    bool res = file != NULL;

    if(file)
    {
        fwrite(decoded, sizeof(char), size, file);
    }

    free(decoded);

    fclose(file);

    AddImageResponse response(res);
    MessageSender::sendMessage(context, response);
}
