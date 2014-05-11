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

#ifndef _RANDOM_HPP_
#define _RANDOM_HPP_

#include <vector>
#include <string>
#include <sstream>
#include <iomanip>

/*!
 * \brief The RandomInitializer class
 */
class RandomInitializer
{
public:
    bool initialized = false;
    RandomInitializer();
    void check();
};

/*!
 * \brief generateRandomHexaString generates random hexadecimal string
 * \param byteLen the byte length of random data converted to hexa string
 * \return the hexadecimal randomized string of 2 times the size of byteLen param
 */
std::string generateRandomHexaString(const int &byteLen);

#endif
