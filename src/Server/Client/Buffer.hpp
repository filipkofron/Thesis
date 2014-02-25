#ifndef _BUFFER_HPP_
#define _BUFFER_HPP_

class Buffer;

#include <cstdlib>
#define BUFFER_DEFAULT_LEN 1024

class Buffer
{
private:
    size_t size;
    int position;
    size_t maxSize;
public:
    char *bytes;
    Buffer();
    ~Buffer();

    void reset();
    void skip(int bytes);
    const int &getPosition();
    void prepare(size_t size);

};

#endif
