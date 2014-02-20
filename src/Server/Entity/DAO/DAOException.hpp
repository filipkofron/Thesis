#ifndef _DAOEXCEPTION_HPP_
#define _DAOEXCEPTION_HPP_

class DAOException;

#include <string>

class DAOException
{
private:
    std::string message;
public:
    DAOException(const std::string &message);
};

#endif
