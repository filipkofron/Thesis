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

#include "VendorDAOMySQL.hpp"

#include "../MySQLManager.hpp"
#include "../../Entity/DAO/DAOException.hpp"

#include <cppconn/prepared_statement.h>
#include <cppconn/resultset.h>

static const char *ADD_VENDOR = "INSERT INTO Vendor (name) VALUES (?)";
static const char *DELETE_VENDOR = "DELETE FROM Vendor WHERE id = ?";
static const char *UPDATE_VENDOR = "UPDATE Vendor SET name = ? WHERE id = ?";
static const char *VENDOR_BY_ID = "SELECT id, name FROM Vendor WHERE id = ?";
static const char *VENDOR_BY_NAME = "SELECT id, name FROM Vendor WHERE name = ?";
static const char *FIND_VENDOR_BY_NAME = "SELECT id, name FROM Vendor WHERE name COLLATE UTF8_GENERAL_CI LIKE ?";
static const char *ALL_VENDORS = "SELECT id, name FROM Vendor";

void VendorDAOMySQL::addVendor(Vendor &vendor, int &newId)
{
    bool exists = false;
    try
    {
        getVendorByName(vendor.getName());
        exists = true;
    }
    catch(DAOException &e)
    {
    }

    if(exists)
    {
        throw DAOException("Vendor already exists!");
    }

    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(ADD_VENDOR));

    ps->setString(1, vendor.getName());

    ps->execute();

    newId = MySQLManager::lastGeneratedId(ch.conn);
}

void VendorDAOMySQL::deleteVendor(Vendor &vendor)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(DELETE_VENDOR));

    ps->setInt(1, vendor.getId());

    ps->execute();
}

void VendorDAOMySQL::updateVendor(const Vendor &vendor)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(UPDATE_VENDOR));

    ps->setString(1, vendor.getName());
    ps->setInt(2, vendor.getId());

    ps->executeUpdate();
}

Vendor VendorDAOMySQL::getVendorById(const int &id)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(VENDOR_BY_ID));

    ps->setInt(1, id);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    if(!res->next())
    {
        throw DAOException("Vendor not found!");
    }

    return Vendor(res->getInt(1), res->getString(2));
}

std::vector<Vendor> VendorDAOMySQL::getAllVendors()
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(ALL_VENDORS));

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Vendor> vendors;
    while(res->next())
    {
        vendors.push_back(Vendor(res->getInt(1), res->getString(2)));
    }

    return vendors;
}

Vendor VendorDAOMySQL::getVendorByName(const std::string &name)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(VENDOR_BY_NAME));

    ps->setString(1, name);

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    if(!res->next())
    {
        throw DAOException("Vendor not found!");
    }

    return Vendor(res->getInt(1), res->getString(2));
}

std::vector<Vendor> VendorDAOMySQL::findVendorByName(const std::string &name)
{
    MySQLManager::ConnectionHolder ch(MySQLManager::getInstance());
    std::unique_ptr<sql::PreparedStatement> ps(ch.conn->prepareStatement(FIND_VENDOR_BY_NAME));

    ps->setString(1, "%" + name + "%");

    std::unique_ptr<sql::ResultSet> res(ps->executeQuery());

    std::vector<Vendor> vendors;
    while(res->next())
    {
        vendors.push_back(Vendor(res->getInt(1), res->getString(2)));
    }

    return vendors;
}
