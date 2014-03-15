#ifndef _DATE_HPP_
#define _DATE_HPP_

class Date;

#include <string>

class Date
{
public:
    static long unixTimeFromMysqlString(const std::string &s);
};

#endif
