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
