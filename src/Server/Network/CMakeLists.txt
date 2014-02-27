set(network_source_files
  BufferReader.hpp
  BufferReader.cpp
  Server.hpp
  Server.cpp
  ReadException.hpp
  ReadException.cpp
  ServerException.hpp
  ServerException.cpp
)
add_library(network ${network_source_files})
