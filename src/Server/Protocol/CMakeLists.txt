set(protocol_source_files
  Message.hpp
  Message.cpp
  LoginResponse.hpp
  LoginResponse.cpp
  LoginRequest.hpp
  LoginRequest.cpp
)
add_library(protocol ${protocol_source_files})
