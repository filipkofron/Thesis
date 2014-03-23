#ifndef _BASE64_HPP_
#define _BASE64_HPP_

#include <inttypes.h>
#include <string>
#include <vector>

int Base64decode_len(const char *bufcoded);
int Base64decode(char *bufplain, const char *bufcoded);
int Base64encode_len(int len);
int Base64encode(char *encoded, const char *string, int len);

#endif
