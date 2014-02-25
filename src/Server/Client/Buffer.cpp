#include "Buffer.hpp"

#include "../Config/Configurator.hpp"
#include "BufferException.hpp"

Buffer::Buffer()
    : size(BUFFER_DEFAULT_LEN), position(0),
      maxSize((size_t) Configurator::getInstance()->getNetworkClientMaxBuffer())
{
    bytes = (char *) malloc(size);
}

Buffer::~Buffer()
{
    free(bytes);
}

void Buffer::prepare(size_t target)
{
    if(target > this->size)
    {
        if(target >= maxSize)
        {
            throw BufferException("Buffer attempted to allocate more than MAX!");
        }
        bytes = (char *) realloc(bytes, target);
    }
}

void Buffer::reset()
{
    position = 0;
}

void Buffer::skip(int bytes)
{
    position += bytes;
}

const int &Buffer::getPosition()
{
    return position;
}
