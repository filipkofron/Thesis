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

class Configurator
{
private:
    Json::Value root;
public:
    Configurator();
    static Configurator *getInstance();
    void reload();

    std::string getMySQLServerAddress();
    std::string getMySQLServerUsername();
    std::string getMySQLServerPassword();
    std::string getMySQLServerDatabase();
    std::string getImageDir();
    std::string getLogFile();
    int getNetworkClientMaxBuffer();
    int getNetworkServerListenPort();
    int getNetworkServerBackLog();
    int getNetworkServerMaxClients();
};

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
