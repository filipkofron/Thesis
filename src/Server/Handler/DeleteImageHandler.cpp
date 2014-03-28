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
