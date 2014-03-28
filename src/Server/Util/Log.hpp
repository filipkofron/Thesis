#ifndef _LOG_HPP_
#define _LOG_HPP_

class Log;

#include <ostream>
#include <fstream>

class Log
{
private:
public:
    static void info(const std::string &msg);
    static void debug(const std::string &msg);
    static void error(const std::string &msg);
};

#endif
