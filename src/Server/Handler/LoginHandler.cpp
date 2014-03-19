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
    std::cout << "LoginHandler: Would handle login with username: " << request->username << std::endl;

    context.setLoggedIn(false);

    if(request->username.empty())
    {
        LoginResponse loginResponse(false, "Empty username.");
        MessageSender::sendMessage(context, loginResponse);
        return;
    }

    std::shared_ptr<UserDAO> dao = DAOFactory::getUserDAO();

    User user;
    try
    {
        user = dao->getUserByUsername(request->username);
    }
    catch(DAOException &e)
    {
        std::cout << "Registering user new user: '" << request->username << "'" << std::endl;
        user = User::makeUser(request->username, *dao);
    }

    context.setUserId(user.getId());
    context.setUsername(user.getUserName());
    context.setLoggedIn(true);

    std::string message = context.getLoggedIn() ? "Login successful." : "Invalid username.";
    LoginResponse loginResponse(context.getLoggedIn(), message);
    MessageSender::sendMessage(context, loginResponse);
}

LoginHandler::~LoginHandler()
{
}
