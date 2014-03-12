#include "Message.hpp"
#include "LoginRequest.hpp"
#include "LoginResponse.hpp"
#include "KeepAlive.hpp"

Message::Message()
{

}

Message::~Message()
{

}

Message *Message::dejsonize(Json::Value &root)
{
    std::string header = root["header"].asString();

    Message *msg = nullptr;
    for(;;)
    {
        if(header.compare(LoginRequest::getStaticHeader()) == 0)
        {
            msg = new LoginRequest();
            break;
        }

        if(header.compare(LoginResponse::getStaticHeader()) == 0)
        {
            msg = new LoginResponse();
            break;
        }

        if(header.compare(KeepAlive::getStaticHeader()) == 0)
        {
            msg = new KeepAlive();
            break;
        }

        break;
    }
    std::cout << "4" << std::endl;

    if(msg)
    {
        msg->_dejsonize(root["content"]);
    }

    return msg;
}

void Message::jsonize(Json::Value &root)
{
     root["header"] = getHeader();

    _jsonize(root["content"]);
}
