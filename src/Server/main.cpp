#include <iostream>
#include <cppconn/driver.h>
#include <cppconn/connection.h>
#include <cppconn/exception.h>
#include <cppconn/statement.h>
#include <cppconn/prepared_statement.h>
#include <jsoncpp/json/writer.h>

#include "Entity/DAO/UserDAOImpl.hpp"

#include "Util/SHA256.hpp"

#include "Protocol/LoginRequest.hpp"

using namespace std;


int main()
{
    try
    {
        auto driver = get_driver_instance();
        auto connection = driver->connect("tcp://localhost:3306/", "root", "toor");
        connection->setAutoCommit(true);
        cout << "getClientInfo: " << connection->getClientInfo() << endl;
        connection->close();
        delete connection;
    }
    catch(sql::SQLException &e)
    {
        cerr << "Error: " << e.what() << endl;
    }

    Json::StyledWriter writer;


    LoginRequest request("Lol", "Supertroll!");


    Json::Value root;
    request.jsonize(root);


    std::cout << "Resp: " << writer.write(root) << std::endl;

    Message *msg(Message::dejsonize(root));

    if(!msg)
    {
        std::cout << "Error!" << std::endl;
        return 1;
    }

    std::cout << "header: " << msg->getHeader() << std::endl;

    LoginRequest *recv((LoginRequest *) msg);

    Handler *handler = recv->createHandler();
    ClientContext context;
    handler->handle(context);
    delete handler;
    delete recv;

    return 0;
}
