set(database_dao_source_files
  UserDAOMySQL.hpp
  UserDAOMySQL.cpp
)
add_library(database_dao ${database_dao_source_files})
