set(entity_source_files
  User.hpp
  User.cpp
)

add_library(entity ${entity_source_files})

add_subdirectory(DAO)
