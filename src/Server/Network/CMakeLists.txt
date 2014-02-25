set(network_source_files
  Server.hpp
  Server.cpp
  ServerException.hpp
  ServerException.cpp
)
add_library(network ${network_source_files})
