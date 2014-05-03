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

#include "Vendor.hpp"

Vendor::Vendor()
{

}

Vendor::Vendor(const int &id, const std::string &name)
    : id(id), name(name)
{

}

const int &Vendor::getId() const
{
    return id;
}

const std::string &Vendor::getName() const
{
    return name;
}

void Vendor::setName(const std::string &name)
{
    this->name = name;
}

Vendor Vendor::makeVendor(const std::string &name, VendorDAO &dao)
{
    Vendor vendor(0, name);
    int newId = 0;

    dao.addVendor(vendor, newId);
    vendor.id = newId;

    return vendor;
}
