#include <iostream>
#include <cppconn/driver.h>
#include <cppconn/connection.h>
#include <cppconn/exception.h>
#include <cppconn/statement.h>
#include <cppconn/prepared_statement.h>
#include <jsoncpp/json/writer.h>

#include "Entity/DAO/DAOException.hpp"

#include "Entity/Food.hpp"

#include "Util/SHA256.hpp"

#include "Protocol/LoginRequest.hpp"
#include "Database/MySQLManager.hpp"

#include "Database/DAO/UserDAOMySQL.hpp"

#include "Network/Server.hpp"

using namespace std;


int main()
{
    Json::Value test;
    test = "fuck";
    try
    {
        test["gtfo"] = "fuck";
    }
    catch(std::runtime_error &e)
    {
        std::cerr << "error: " << e.what() << std::endl;
    }

    try
    {
        test["gtfo"].asInt();
    }
    catch(std::runtime_error &e)
    {
        std::cerr << "error: " << e.what() << std::endl;
    }

    Server server;
    server.initialize();
    server.run();

    return 0;
}
