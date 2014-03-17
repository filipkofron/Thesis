#include "Date.hpp"

#include <ctime>
#include <cstdlib>
#include <cstring>

int64_t Date::unixTimeFromMysqlString(const std::string &s)
{
    struct tm rawtime;
    memset(&rawtime, 1, sizeof(rawtime));
    strptime(s.c_str(), "%Y-%m-%d %H:%M:%S", &rawtime);

    time_t t = mktime(&rawtime);
    return (int64_t) t;
}

std::string Date::unixTimeToMysqlString(const int64_t &t)
{
    time_t rawtime;
    struct tm timeinfo;
    rawtime = (time_t) t;
    localtime_r(&rawtime, &timeinfo);
    char buf[128];
    memset(&buf, 1, sizeof(buf));
    strftime(buf, sizeof(buf), "%Y-%m-%d %H:%M:%S", &timeinfo);
    return std::string(buf);
}
