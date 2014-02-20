#ifndef _SHA256_HPP_
#define _SHA256_HPP_

#include <string>
#include <sstream>
#include <iomanip>

extern "C" {
    #include <openssl/sha.h>
}

std::string sha256(const std::string &plaintext);

#endif
