#ifndef _MYSQLMANAGER_HPP_
#define _MYSQLMANAGER_HPP_

class MySQLManager;

#include <queue>
#include <mutex>

#include <cppconn/driver.h>
#include <cppconn/connection.h>

class MySQLManager
{
private:
    std::mutex queueMutex;
    std::queue<sql::Connection *> connections;
    sql::Driver *driver;
    sql::Connection *getConnection();
    sql::Connection *openNewConnection();
    void returnConnection(sql::Connection *conn);
public:
    class ConnectionHolder
    {
    private:
        MySQLManager *mysqlManager;
    public:
        ConnectionHolder(MySQLManager *mysqlManager);
        ~ConnectionHolder();
        sql::Connection * const conn;
    };

    MySQLManager();
    ~MySQLManager();

    static int lastGeneratedId(sql::Connection *conn);

    void closeAll();

    static MySQLManager *getInstance();
};

#endif
