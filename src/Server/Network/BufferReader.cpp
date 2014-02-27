#include "BufferReader.hpp"
#include "ReadException.hpp"
#include <unistd.h>
#include <iostream>

void BufferReader::readFully(const int &sd, char *buffer, const int &len)
{
    int readAlready = 0;

    while(len - readAlready)
    {
        int ret = read(sd, buffer + readAlready, len - readAlready);
        if(ret <= 0 || ret > (len - readAlready))
        {
            throw ReadException();
        }
        readAlready += ret;
    }
}

int BufferReader::readSize(const int &sd)
{
    unsigned char buffer[4];

    readFully(sd, (char *) buffer, sizeof(buffer));

    int size = 0;
    size += (buffer[0] & 0xFF) << 24;
    size += (buffer[1] & 0xFF) << 16;
    size += (buffer[2] & 0xFF) << 8;
    size += (buffer[3] & 0xFF);

    return size;
}

void BufferReader::readBuffer(std::shared_ptr<Context> context)
{
    int bufferSize = readSize(context->getClientSD());
    std::cout << "Client wants to allocate: " << bufferSize << std::endl;

    Buffer &buffer = context->getBuffer();
    /* prepare for size bufferSize + 1 for the final null to terminate the string, this will not be needed for compressed data */
    buffer.prepare((size_t) bufferSize + 1);

    std::cout << "Will read buffer of size: " << bufferSize << std::endl;

    readFully(context->getClientSD(), buffer.getBytes(), bufferSize);
    buffer.getBytes()[bufferSize] = '\0';
}
