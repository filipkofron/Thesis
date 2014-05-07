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

#include "Configurator.hpp"
#include <fstream>

Configurator::Configurator()
{
    reload();
}

static Configurator instance;

Configurator *Configurator::getInstance()
{
    return &instance;
}

void Configurator::reload()
{
    Json::Reader reader;
    std::ifstream file("settings.json");
    if(file.good())
    {
        reader.parse(file, root);
    }
    else
    {
        reader.parse(DEFAULT_JSON_DOCUMENT, root);
    }
    file.close();
}

std::string Configurator::getMySQLServerAddress()
{
    return root["database"]["mysql"]["address"].asString();
}

std::string Configurator::getMySQLServerUsername()
{
    return root["database"]["mysql"]["username"].asString();
}

std::string Configurator::getMySQLServerPassword()
{
    return root["database"]["mysql"]["password"].asString();
}

std::string Configurator::getMySQLServerDatabase()
{
    return root["database"]["mysql"]["database"].asString();
}

std::string getLogFile()
{
    return "log.txt";
}

int Configurator::getNetworkClientMaxBuffer()
{
    return root["network"]["client"]["max_buffer"].asInt();
}

int Configurator::getNetworkServerListenPort()
{
    return root["network"]["server"]["listen_port"].asInt();
}

int Configurator::getNetworkServerBackLog()
{
    return root["network"]["server"]["back_log"].asInt();
}

int Configurator::getNetworkServerMaxClients()
{
    return root["network"]["server"]["max_clients"].asInt();
}

std::string Configurator::getImageDir()
{
    return "/var/www/img/";
}
