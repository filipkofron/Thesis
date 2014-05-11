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

/*!
 * The MySQLManager class provides connection to the MySQL server.
 * The class allows to cache existing connections.
 * The functionality of this class cannot be accesed directly,
 * using the ConnectionHolder provides this access and makes sure
 * that the connections are all returned upon the holder destruction.
 */
class MySQLManager
{
private:
    std::mutex queueMutex; //!< mutex for the queue operations
    std::queue<sql::Connection *> connections; //!< queue of MySQL connections
    sql::Driver *driver; //!< the driver instance
    sql::Connection *getConnection(); //!< retrieves a connection from the queue or calls the openNewConnection to create new connection
    sql::Connection *openNewConnection(); //!< creates new connection go the MySQL Server
    void returnConnection(sql::Connection *conn); //!< returns a MySQL connection back to the queue
public:
    class ConnectionHolder
    {
    private:
        MySQLManager *mysqlManager;
    public:
        ConnectionHolder(MySQLManager *mysqlManager); //!< constructs the Holder capturing an instance of MySQL Manager and retrieving an instance of MySQL connection
        ~ConnectionHolder(); //!< returns back the retrieved connection to the MySQL manager
        sql::Connection * const conn; //!< the MySQL connetion
    };

    MySQLManager();
    ~MySQLManager();

		/*!
		 * Returns the last generated id from the given MySQL Connection.
		 * The insert query must be the only called before this method
		 */
    static int lastGeneratedId(sql::Connection *conn);

		/*!
		 * Closes all MySQL connections, on server shutdown for example
		 */
    void closeAll();

		/*!
		 * Returns the default static instance of MySQL Manager
		 */
    static MySQLManager *getInstance();
};

#endif
