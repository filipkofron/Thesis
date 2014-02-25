#ifndef _BUFFEREXCEPTION_HPP_
#define _BUFFEREXCEPTION_HPP_

class BufferException;

#include <string>

class BufferException
{
private:
    const char *message;
public:
    BufferException(const char *message);
    const char *what();
};

#endif
