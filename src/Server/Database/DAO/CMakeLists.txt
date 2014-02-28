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
  InventoryDAOMySQL.hpp
  InventoryDAOMySQL.cpp
  ReviewDAOMySQL.hpp
  ReviewDAOMySQL.cpp
  VendorDAOMySQL.hpp
  VendorDAOMySQL.cpp
)
add_library(database_dao ${database_dao_source_files})


target_link_libraries(database_dao database entity entity_dao)
