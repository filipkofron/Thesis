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
