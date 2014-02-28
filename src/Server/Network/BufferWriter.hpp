#ifndef _BUFFERWRITER_HPP_
#define _BUFFERWRITER_HPP_

class BufferWriter;

#include "../Client/Buffer.hpp"
#include "../Client/Context.hpp"

class BufferWriter
{
private:
    static void writeSize(const int &sd, const int &size);
    static void writeFully(const int &sd, const char *buffer, const int &len);
public:
    static void writeBuffer(Context &context);
};

#endif
