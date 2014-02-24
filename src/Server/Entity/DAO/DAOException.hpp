#ifndef _DAOEXCEPTION_HPP_
#define _DAOEXCEPTION_HPP_

class DAOException;

#include <string>

class DAOException
{
private:
    const char *message;
public:
    DAOException(const char *message);
    const char *what();
};

#endif
