set(database_dao_source_files
  UserDAOMySQL.hpp
  UserDAOMySQL.cpp
  CategoryDAOMySQL.hpp
  CategoryDAOMySQL.cpp
  EditDAOMySQL.hpp
  EditDAOMySQL.cpp
  FoodDAOMySQL.hpp
  FoodDAOMySQL.cpp
  ImageDAOMySQL.hpp
  ImageDAOMySQL.cpp
)
add_library(database_dao ${database_dao_source_files})
