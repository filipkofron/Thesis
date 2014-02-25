#ifndef _SERVEREXCEPTION_HPP_
#define _SERVEREXCEPTION_HPP_

class ServerException;

#include <string>

class ServerException
{
private:
    const char *message;
public:
    ServerException(const char *message);
    const char *what();
};


#endif
