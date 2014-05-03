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

#include "Random.hpp"
#include <time.h>
#include <stdlib.h>

RandomInitializer::RandomInitializer()
{
    check();
}

void RandomInitializer::check()
{
    if(!initialized)
    {
        srand(time(NULL));
        initialized = true;
    }
}

static RandomInitializer randomInitializer;

std::string generateRandomHexaString(const int &byteLen)
{
    randomInitializer.check();
    std::stringstream ss;
    for(int i = 0; i < byteLen; i++)
    {
        ss << std::hex << std::setw(2) << std::setfill('0') << (rand() & 0xFF);
    }

    return ss.str();
}
