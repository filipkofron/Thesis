#include "Message.hpp"
#include "LoginRequest.hpp"
#include "LoginResponse.hpp"

Message::Message()
{

}

Message::~Message()
{

}

Message *Message::dejsonize(const Json::Value &root)
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

        break;
    }

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
