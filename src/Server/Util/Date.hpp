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

#ifndef _DATE_HPP_
#define _DATE_HPP_

class Date;

#include <string>
#include <inttypes.h>

class Date
{
public:
    /*!
     * \brief unixTimeFromMysqlString converts MySQL Date format encoded in string to unix time in miliseconds
     * \param mysql date format encoded in string
     * \return unix time in miliseconds
     */
    static int64_t unixTimeFromMysqlString(const std::string &s);

    /*!
     * \brief unixTimeToMysqlString converts MySQL Date format encoded in string from unix time in miliseconds
     * \param unix time in miliseconds
     * \return mysql date format encoded in string
     */
    static std::string unixTimeToMysqlString(const int64_t &t);

    /*!
     * \brief currentTimeMilis calculates the current unix time in miliseconds on server
     * \return unix time in miliseconds
     */
    static int64_t currentTimeMilis();
};

#endif
