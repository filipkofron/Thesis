set(util_source_files
  SHA256.hpp
  SHA256.cpp
  Random.hpp
  Random.cpp
  Date.hpp
  Date.cpp
  Base64.hpp
  Base64.cpp
)
add_library(util ${util_source_files})
