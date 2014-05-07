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
