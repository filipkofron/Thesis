set(connected_client_source_files
  ClientThread.hpp
  ClientThread.cpp
  Context.hpp
  Context.cpp
  BufferException.hpp
  BufferException.cpp
  Buffer.hpp
  Buffer.cpp
)
add_library(connected_client ${connected_client_source_files})

target_link_libraries( connected_client network )
