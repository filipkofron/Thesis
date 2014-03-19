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

void json_exception_test()
{
    std::cout << __FILE__ << ":" << __LINE__
              <<" if the following exits this program, you need to upgrade the JSONCPP library to use exceptions" << std::endl;

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
}

int main()
{
    std::cout << "> [Food inventory server]" << std::endl;
    std::cout << "> Author: Filip Kofron (filip.kofron.cz@gmail.com)" << std::endl;
    std::cout << "> Build: " << __DATE__ << " " << __TIME__ << std::endl;
    std::cout << std::endl;
    json_exception_test();

    std::cout << "> [test]: " << Food::fixGtin("90490880") << std::endl;

    Server server;
    server.initialize();
    server.run();

    return 0;
}
