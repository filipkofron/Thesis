#include "LoginHandler.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Entity/DAO/DAOException.hpp"
#include "../Network/MessageSender.hpp"
#include "../Protocol/LoginResponse.hpp"

LoginHandler::LoginHandler(LoginRequest *request)
    : request(request)
{

}

void LoginHandler::handle(Context &context)
{
    std::cout << "LoginHandler: Would handle login with username: " << request->username << ":" << request->password << std::endl;

    context.setLoggedIn(false);

    if(request->username.empty() || request->password.empty())
    {
        LoginResponse loginResponse(false, "Empty username/password.");
        MessageSender::sendMessage(context, loginResponse);
        return;
    }

    try
    {
        std::shared_ptr<UserDAO> dao = DAOFactory::getUserDAO();
        User user = dao->getUserByUsername(request->username);
        context.setUserId(user.getId());
        context.setUsername(user.getUserName());
        context.setLoggedIn(user.checkPassword(request->password));
    }
    catch(DAOException &dao)
    {
        std::cout << "LoginHandler: User " << request->username << " doesn't exist!" << std::endl;
    }

    std::string message = context.getLoggedIn() ? "Login successful." : "Invalid username/password.";
    LoginResponse loginResponse(context.getLoggedIn(), message);
    MessageSender::sendMessage(context, loginResponse);
}

LoginHandler::~LoginHandler()
{
}
