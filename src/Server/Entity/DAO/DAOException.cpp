#include "DAOException.hpp"

DAOException::DAOException(const char *message)
    : message(message)
{

}

const char *DAOException::what()
{
    return message;
}
