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

#ifndef _VENDORDAOMYSQL_HPP_
#define _VENDORDAOMYSQL_HPP_

class VendorDAO;

#include "../../Entity/DAO/VendorDAO.hpp"

class VendorDAOMySQL : public VendorDAO
{
public:
    VendorDAOMySQL() { }
    virtual ~VendorDAOMySQL() { }

    virtual void addVendor(Vendor &vendor, int &newId);
    virtual void deleteVendor(Vendor &vendor);
    virtual void updateVendor(const Vendor &vendor);
    virtual Vendor getVendorById(const int &id);
    virtual Vendor getVendorByName(const std::string &name);
    virtual std::vector<Vendor> getAllVendors();
    virtual std::vector<Vendor> findVendorByName(const std::string &name);
};

#endif
