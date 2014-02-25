#include "ServerException.hpp"

ServerException::ServerException(const char *message)
    : message(message)
{

}

const char *ServerException::what()
{
    return message;
}
