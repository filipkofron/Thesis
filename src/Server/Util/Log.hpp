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

#ifndef _LOG_HPP_
#define _LOG_HPP_

class Log;

#include <ostream>
#include <fstream>

/*!
 * \brief The Log class provides very basic logging methods.
 */
class Log
{
private:
public:
    /*!
     * \brief info prints an informative message, defaults to standard output stream
     * \param msg the printed message
     */
    static void info(const std::string &msg);

    /*!
     * \brief debug prints a debug message, defaults to standard output stream
     * \param msg the printed message
     */
    static void debug(const std::string &msg);

    /*!
     * \brief error prints an error message, defaults to standard error stream
     * \param msg the printed message
     */
    static void error(const std::string &msg);
};

#endif
