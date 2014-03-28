#include "Log.hpp"
#include "Date.hpp"
#include <iostream>

void Log::info(const std::string &msg)
{
    std::cout << "[I " << Date::unixTimeToMysqlString(Date::currentTimeMilis() / 1000) << "] " << msg << std::endl;
}

void Log::debug(const std::string &msg)
{
    std::cout << "[D " << Date::unixTimeToMysqlString(Date::currentTimeMilis() / 1000) << "] " << msg << std::endl;
}

void Log::error(const std::string &msg)
{
    std::cerr << "[E " << Date::unixTimeToMysqlString(Date::currentTimeMilis() / 1000) << "] " << msg << std::endl;
}
