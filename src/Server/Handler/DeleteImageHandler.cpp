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

#include "DeleteImageHandler.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Protocol/DeleteImageResponse.hpp"
#include "../Network/MessageSender.hpp"
#include "../Config/Configurator.hpp"
#include "../Util/Date.hpp"
#include <unistd.h>

DeleteImageHandler::DeleteImageHandler(DeleteImageRequest *request)
    : request(request)
{

}

DeleteImageHandler::~DeleteImageHandler()
{

}

void DeleteImageHandler::handle(Context &context)
{
    if(!context.getLoggedIn())
    {
        DeleteImageResponse response(false);
        MessageSender::sendMessage(context, response);
    }

    std::shared_ptr<EditDAO> editDao = DAOFactory::getEditDAO();
    std::shared_ptr<ImageDAO> imageDao = DAOFactory::getImageDAO();

    Image image = imageDao->getImageById(request->id);

    int foodId = image.getFoodId();

    Edit edit = Edit::makeEdit(context.getUserId(), foodId, Date::unixTimeToMysqlString(Date::currentTimeMilis() / 1000), "User deleted image.", *editDao);

    int imgId = image.getId();
    imageDao->deleteImage(image);

    std::string path = Configurator::getInstance()->getImageDir() + "/" + std::to_string(imgId) + ".jpg";
    int res = unlink(path.c_str());

    DeleteImageResponse response(res == 0);
    MessageSender::sendMessage(context, response);
}
