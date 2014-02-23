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
