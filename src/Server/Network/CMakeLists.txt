set(network_source_files
  BufferReader.hpp
  BufferReader.cpp
  BufferWriter.hpp
  BufferWriter.cpp
  MessageSender.hpp
  MessageSender.cpp
  Server.hpp
  Server.cpp
  ReadException.hpp
  ReadException.cpp
  WriteException.hpp
  WriteException.cpp
  ServerException.hpp
  ServerException.cpp
)
add_library(network ${network_source_files})
