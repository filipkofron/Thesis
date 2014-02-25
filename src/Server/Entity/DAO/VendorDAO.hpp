#ifndef _VENDORDAO_HPP_
#define _VENDORDAO_HPP_

class VendorDAO;

#include "../Vendor.hpp"

#include <string>
#include <vector>

class VendorDAO
{
public:
    VendorDAO() { }
    virtual ~VendorDAO() { }

    virtual void addVendor(Vendor &vendor, int &newId) = 0;
    virtual void deleteVendor(Vendor &vendor) = 0;
    virtual void updateVendor(const Vendor &vendor) = 0;
    virtual Vendor getVendorById(const int &id) = 0;
    virtual Vendor getVendorByName(const std::string &name) = 0;
    virtual std::vector<Vendor> findVendorByName(const std::string &name) = 0;
};

#endif
