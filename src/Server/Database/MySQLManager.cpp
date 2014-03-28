#include "MySQLManager.hpp"

#include <memory>

#include <cppconn/exception.h>
#include <cppconn/prepared_statement.h>
#include <cppconn/resultset.h>

#include "../Config/Configurator.hpp"

#include "../Util/Log.hpp"

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
        Log::debug("MySQL Manager: Closing a connection!");
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
            Log::error(std::string("Cannot open new connection to the MySQL Server!: ") + std::string(e.what()));
            connection = nullptr;
        }
    }

    return connection;
}

sql::Connection *MySQLManager::openNewConnection()
{
    Log::debug("MySQL Manager: Opening a connection!");
    sql::Connection *conn = driver->connect(Configurator::getInstance()->getMySQLServerAddress(),
                                            Configurator::getInstance()->getMySQLServerUsername(),
                                            Configurator::getInstance()->getMySQLServerPassword());
    conn->setAutoCommit(true);
    conn->setSchema(Configurator::getInstance()->getMySQLServerDatabase());
    return conn;
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

static const char * LAST_ID = "SELECT LAST_INSERT_ID()";

int MySQLManager::lastGeneratedId(sql::Connection *conn)
{
    std::unique_ptr<sql::PreparedStatement> ps(conn->prepareStatement(LAST_ID));
    std::unique_ptr<sql::ResultSet> rs(ps->executeQuery());
    return rs->next() ? rs->getInt(1) : 0;
}
