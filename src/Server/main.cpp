#include <iostream>
#include <cppconn/driver.h>
#include <cppconn/connection.h>
#include <cppconn/exception.h>
#include <cppconn/statement.h>
#include <cppconn/prepared_statement.h>

#include "Entity/DAO/UserDAOImpl.hpp"

#include "Util/SHA256.hpp"

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

    UserDAOImpl daoImpl;
    UserDAO &dao = daoImpl;
    User user = User::makeUser("pepa", "zdepa", dao);

    cout << "Salt: " << user.getSalt() << endl;
    cout << "Hash: " << user.getPassword() << endl;

    return 0;
}

