#include "MySQLManager.hpp"

#include <iostream>

#include <cppconn/exception.h>

#include "../Config/Configurator.hpp"

MySQLManager::MySQLManager()
{
    driver = get_driver_instance();
}

MySQLManager::~MySQLManager()
{
    closeAll();
}

void MySQLManager::closeAll()
{
    std::lock_guard<std::mutex> lock(queueMutex);
    while (!connections.empty())
    {
        std::cout << "Closing a connection!" << std::endl;
        connections.front()->close();
        delete connections.front();
        connections.pop();
    }
}

static MySQLManager instance;

MySQLManager *MySQLManager::getInstance()
{
    return &instance;
}

sql::Connection *MySQLManager::getConnection()
{
    std::lock_guard<std::mutex> lock(queueMutex);

    sql::Connection *connection = nullptr;

    if(!connections.empty())
    {
        connection = connections.front();
        connections.pop();

        if(connection->isClosed())
        {
            delete connection;
            connection = nullptr;
        }
    }

    if(connection == nullptr)
    {
        try
        {
            connection = openNewConnection();
        }
        catch(sql::SQLException &e)
        {
            std::cerr << "Cannot open new connection to the MySQL Server!: " << e.what() << std::endl;
            connection = nullptr;
        }
    }

    return connection;
}

sql::Connection *MySQLManager::openNewConnection()
{
    std::cout << "Opening a new connection with: " << Configurator::getInstance()->getMySQLServerAddress() << " - " <<
                 Configurator::getInstance()->getMySQLServerUsername() << " - " <<
                 Configurator::getInstance()->getMySQLServerPassword() << std::endl;
    return driver->connect(Configurator::getInstance()->getMySQLServerAddress(),
                           Configurator::getInstance()->getMySQLServerUsername(),
                           Configurator::getInstance()->getMySQLServerPassword());
}

void MySQLManager::returnConnection(sql::Connection *conn)
{
    std::lock_guard<std::mutex> lock(queueMutex);

    if(conn->isClosed())
    {
        delete conn;
        conn = nullptr;
    }

    if(conn != nullptr)
    {
        connections.push(conn);
    }
}

MySQLManager::ConnectionHolder::ConnectionHolder(MySQLManager *mysqlManager)
    : conn(mysqlManager->getConnection())
{
    this->mysqlManager = mysqlManager;
}

MySQLManager::ConnectionHolder::~ConnectionHolder()
{
    if(conn != nullptr)
    {
        mysqlManager->returnConnection(conn);
    }
}
