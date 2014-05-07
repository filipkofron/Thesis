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

#include "LoginHandler.hpp"
#include "../Entity/DAO/DAOFactory.hpp"
#include "../Entity/DAO/DAOException.hpp"
#include "../Network/MessageSender.hpp"
#include "../Protocol/LoginResponse.hpp"
#include "../Util/Log.hpp"

LoginHandler::LoginHandler(LoginRequest *request)
    : request(request)
{

}

void LoginHandler::handle(Context &context)
{
    context.setLoggedIn(false);

    if(request->username.empty())
    {
        LoginResponse loginResponse(false, "Empty username.");
        MessageSender::sendMessage(context, loginResponse);
        return;
    }

    std::shared_ptr<UserDAO> dao = DAOFactory::getUserDAO();

    Log::debug(std::string("Login attempt with username: '") + request->username + std::string("'"));

    User user;
    try
    {
        user = dao->getUserByUsername(request->username);
    }
    catch(DAOException &e)
    {
        Log::debug(std::string("Registering user new user: '") + request->username + std::string("'"));
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
