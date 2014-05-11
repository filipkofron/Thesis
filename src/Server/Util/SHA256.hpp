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

#ifndef _SHA256_HPP_
#define _SHA256_HPP_

#include <string>
#include <sstream>
#include <iomanip>

extern "C"
{
    #include <openssl/sha.h>
}

/*!
 * \brief sha256 calculates the sha256 hash of the given plaintext
 * \param plaintext the given plaintext
 * \return the calculated hash
 */
std::string sha256(const std::string &plaintext);

#endif
