#ifndef _BASE64_HPP_
#define _BASE64_HPP_

#include <inttypes.h>
#include <string>
#include <vector>

namespace Base64
{
    std::string encode(const unsigned char *buf, unsigned int bufLen);
    std::vector<uint8_t> decode(std::string const& encoded_string);
}

#endif
