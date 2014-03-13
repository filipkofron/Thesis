set(protocol_source_files
  Message.hpp
  Message.cpp
  LoginResponse.hpp
  LoginResponse.cpp
  LoginRequest.hpp
  LoginRequest.cpp
  GetInventoryRequest.hpp
  GetInventoryRequest.cpp
  GetInventoryResponse.hpp
  GetInventoryResponse.cpp
  KeepAlive.hpp
  KeepAlive.cpp
)
add_library(protocol ${protocol_source_files})
