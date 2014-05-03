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

#include "Date.hpp"

#include <ctime>
#include <cstdlib>
#include <cstring>
#include <chrono>

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

int64_t Date::currentTimeMilis()
{
    namespace sc = std::chrono;

    auto time = sc::system_clock::now();
    auto since_epoch = time.time_since_epoch();
    auto millis = sc::duration_cast<sc::milliseconds>(since_epoch);

    return (int64_t) millis.count();
}
