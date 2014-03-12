set(protocol_source_files
  Message.hpp
  Message.cpp
  LoginResponse.hpp
  LoginResponse.cpp
  LoginRequest.hpp
  LoginRequest.cpp
  KeepAlive.hpp
  KeepAlive.cpp
)
add_library(protocol ${protocol_source_files})
