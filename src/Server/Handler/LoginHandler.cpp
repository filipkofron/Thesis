#include "LoginHandler.hpp"

LoginHandler::LoginHandler(LoginRequest *request)
    : request(request)
{

}

void LoginHandler::handle(ClientContext &clientContext)
{
    // TODO: Handle login!
}

LoginHandler::~LoginHandler()
{
}
