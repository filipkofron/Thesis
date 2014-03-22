#ifndef _DATE_HPP_
#define _DATE_HPP_

class Date;

#include <string>
#include <inttypes.h>

class Date
{
public:
    static int64_t unixTimeFromMysqlString(const std::string &s);
    static std::string unixTimeToMysqlString(const int64_t &t);
    static int64_t currentTimeMilis();
};

#endif
