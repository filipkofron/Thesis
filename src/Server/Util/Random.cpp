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
