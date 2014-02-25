#include "BufferException.hpp"

BufferException::BufferException(const char *message)
    : message(message)
{

}

const char *BufferException::what()
{
    return message;
}
