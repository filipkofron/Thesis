#include "BufferWriter.hpp"
#include "WriteException.hpp"
#include <cstring>
#include <unistd.h>
#include <iostream>

void BufferWriter::writeSize(const int &sd, const int &size)
{
    unsigned char buffer[4];

    buffer[0] = ((size >> 24) & 0xFF);
    buffer[1] = ((size >> 16) & 0xFF);
    buffer[2] = ((size >> 8) & 0xFF);
    buffer[3] = (size & 0xFF);

    writeFully(sd, (char *) buffer, sizeof(buffer));
}

void BufferWriter::writeFully(const int &sd, const char *buffer, const int &len)
{
    int wroteAlready = 0;

    while(len - wroteAlready)
    {
        int ret = write(sd, buffer + wroteAlready, len - wroteAlready);

        if(ret <= 0 || ret > (len - wroteAlready))
        {
            throw WriteException();
        }
        wroteAlready += ret;
    }
}

void BufferWriter::writeBuffer(Context &context)
{
    /**
     * @todo this won't work with compressed buffer.
     */
    int lenBytes = strlen(context.getBuffer().getBytes());

    std::cout << "Want to send: '" << context.getBuffer().getBytes() << "'" << std::endl;

    writeSize(context.getClientSD(), lenBytes);

    writeFully(context.getClientSD(), context.getBuffer().getBytes(), lenBytes);
}
