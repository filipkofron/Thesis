#include "Message.hpp"
#include "LoginRequest.hpp"
#include "LoginResponse.hpp"
#include "KeepAlive.hpp"
#include "GetInventoryRequest.hpp"
#include "GetInventoryResponse.hpp"
#include "GetFoodDetailRequest.hpp"
#include "GetFoodDetailResponse.hpp"
#include "GetFoodItemRequest.hpp"
#include "GetFoodItemResponse.hpp"
#include "EditInventoryRequest.hpp"
#include "EditInventoryResponse.hpp"
#include "DeleteInventoryRequest.hpp"
#include "DeleteInventoryResponse.hpp"
#include "EditFoodRequest.hpp"
#include "EditFoodResponse.hpp"
#include "GetFoodBaseRequest.hpp"
#include "GetFoodBaseResponse.hpp"
#include "AddImageRequest.hpp"
#include "AddImageResponse.hpp"
#include "DeleteImageRequest.hpp"
#include "DeleteImageResponse.hpp"
#include "SetUserReviewRequest.hpp"
#include "SetUserReviewResponse.hpp"


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

        if(header.compare(GetInventoryRequest::getStaticHeader()) == 0)
        {
            msg = new GetInventoryRequest();
            break;
        }

        if(header.compare(GetInventoryResponse::getStaticHeader()) == 0)
        {
            msg = new GetInventoryResponse();
            break;
        }

        if(header.compare(GetFoodDetailRequest::getStaticHeader()) == 0)
        {
            msg = new GetFoodDetailRequest();
            break;
        }

        if(header.compare(GetFoodDetailResponse::getStaticHeader()) == 0)
        {
            msg = new GetFoodDetailResponse();
            break;
        }

        if(header.compare(GetFoodItemRequest::getStaticHeader()) == 0)
        {
            msg = new GetFoodItemRequest();
            break;
        }

        if(header.compare(GetFoodItemResponse::getStaticHeader()) == 0)
        {
            msg = new GetFoodItemResponse();
            break;
        }

        if(header.compare(EditInventoryRequest::getStaticHeader()) == 0)
        {
            msg = new EditInventoryRequest();
            break;
        }

        if(header.compare(EditInventoryResponse::getStaticHeader()) == 0)
        {
            msg = new EditInventoryResponse();
            break;
        }

        if(header.compare(DeleteInventoryRequest::getStaticHeader()) == 0)
        {
            msg = new DeleteInventoryRequest();
            break;
        }

        if(header.compare(DeleteInventoryResponse::getStaticHeader()) == 0)
        {
            msg = new DeleteInventoryResponse();
            break;
        }

        if(header.compare(EditFoodRequest::getStaticHeader()) == 0)
        {
            msg = new EditFoodRequest();
            break;
        }

        if(header.compare(EditFoodResponse::getStaticHeader()) == 0)
        {
            msg = new EditFoodResponse();
            break;
        }

        if(header.compare(GetFoodBaseRequest::getStaticHeader()) == 0)
        {
            msg = new GetFoodBaseRequest();
            break;
        }

        if(header.compare(GetFoodBaseResponse::getStaticHeader()) == 0)
        {
            msg = new GetFoodBaseResponse();
            break;
        }


        if(header.compare(AddImageRequest::getStaticHeader()) == 0)
        {
            msg = new AddImageRequest();
            break;
        }

        if(header.compare(AddImageResponse::getStaticHeader()) == 0)
        {
            msg = new AddImageResponse();
            break;
        }


        if(header.compare(DeleteImageRequest::getStaticHeader()) == 0)
        {
            msg = new DeleteImageRequest();
            break;
        }

        if(header.compare(DeleteImageResponse::getStaticHeader()) == 0)
        {
            msg = new DeleteImageResponse();
            break;
        }

        if(header.compare(SetUserReviewRequest::getStaticHeader()) == 0)
        {
            msg = new SetUserReviewRequest();
            break;
        }

        if(header.compare(SetUserReviewResponse::getStaticHeader()) == 0)
        {
            msg = new SetUserReviewResponse();
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
