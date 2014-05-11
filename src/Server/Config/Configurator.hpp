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

#ifndef _CONFIGURATOR_HPP_
#define _CONFIGURATOR_HPP_

class Configurator;

#include <jsoncpp/json/value.h>
#include <jsoncpp/json/reader.h>

//! The Configurator class
/*!
 * The Configurator class introduces several methods to retrieve specific
 * configuration of the server from file "settings.json" in PATH variable.
 *
 * When no file is found, the Configurator loads defaults as specified below.
 */
class Configurator
{
private:
    Json::Value root; //!< root of the JSON tree
public:
    Configurator(); //!< The default constructor, calls reload()
    static Configurator *getInstance(); //!< Retrieves the static instance of this class
    void reload(); //!< Reloads the configuration from settings.json or uses defaults below

    std::string getMySQLServerAddress(); //!< Retrieves MySQL Server address
    std::string getMySQLServerUsername(); //!< Retrieves MySQL Server Username
    std::string getMySQLServerPassword(); //!< Retrieves MySQL Server Plain Password
    std::string getMySQLServerDatabase(); //!< Retrieves MySQL Server Database name
    std::string getImageDir(); //!< Retrieves the directory for storing images
    std::string getLogFile(); //!< Retrieves the log file path
    int getNetworkClientMaxBuffer(); //!< Retrieves the MAX number of bytes per client buffer
    int getNetworkServerListenPort(); //!< Retrieves server listening port number
    int getNetworkServerBackLog(); //!< Retrieves the max number of connections in the backlog
    int getNetworkServerMaxClients(); //!< Retrieves the MAX number of clients the server accepts
};

/*!
 * The defaults that are loaded when the settings.json is not available.
 */
#define DEFAULT_JSON_DOCUMENT \
        "{ " \
        "\"database\" : {" \
      "\"mysql\" : {" \
         "\"address\" : \"tcp://localhost:3306/\"," \
         "\"password\" : \"toor\"," \
         "\"username\" : \"root\"," \
         "\"database\" : \"mydb\"" \
      "}" \
   "}," \
    "\"network\" : {" \
          "\"client\" : {" \
    "\"max_buffer\" : 131072" \
            "}," \
            "\"server\" : {" \
            "\"listen_port\" : 4040," \
            "\"back_log\" : 32," \
            "\"max_clients\" : 256" \
"}" \
"    }" \
"}"

#endif
