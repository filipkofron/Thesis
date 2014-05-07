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

#ifndef _VENDOR_HPP_
#define _VENDOR_HPP_

#include <string>

class Vendor;

#include "DAO/VendorDAO.hpp"

class Vendor
{
private:
    int id;
    std::string name;
public:
    Vendor();
    Vendor(const int &id, const std::string &name);

    const int &getId() const;
    const std::string &getName() const;

    void setName(const std::string &name);

    static Vendor makeVendor(const std::string &name, VendorDAO &dao);
};

#endif
