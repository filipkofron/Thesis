#ifndef _RANDOM_HPP_
#define _RANDOM_HPP_

#include <vector>
#include <string>
#include <sstream>
#include <iomanip>

class RandomInitializer
{
public:
    bool initialized = false;
    RandomInitializer();
    void check();
};

std::string generateRandomHexaString(const int &byteLen);

#endif
