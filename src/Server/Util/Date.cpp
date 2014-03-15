#include "Date.hpp"

#include <ctime>
#include <cstdlib>

long Date::unixTimeFromMysqlString(const std::string &s)
{
    struct tm tmlol;
    strptime(s.c_str(), "%Y-%m-%d %H:%M:%S", &tmlol);

    time_t t = mktime(&tmlol);
    return (long) t;
}
