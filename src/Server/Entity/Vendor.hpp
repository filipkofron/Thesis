#ifndef _VENDOR_HPP_
#define _VENDOR_HPP_

#include <string>

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
};

#endif
