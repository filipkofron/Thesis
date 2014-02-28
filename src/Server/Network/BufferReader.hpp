#ifndef _BUFFERREADER_HPP_
#define _BUFFERREADER_HPP_

class BufferReader;

#include "../Client/Buffer.hpp"
#include "../Client/Context.hpp"

#include <memory>

class BufferReader
{
private:
    static int readSize(const int &sd);
    static void readFully(const int &sd, char *buffer, const int &len);
public:
    static void readBuffer(Context &context);
};

#endif
