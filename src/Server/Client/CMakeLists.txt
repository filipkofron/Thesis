set(client_source_files
  ClientThread.hpp
  ClientThread.cpp
  Context.hpp
  Context.cpp
  BufferException.hpp
  BufferException.cpp
  Buffer.hpp
  Buffer.cpp
)
add_library(client ${client_source_files})

target_link_libraries( client network )
