set(entity_dao_source_files
  DAOFactory.hpp
  DAOFactory.cpp
  CategoryDAO.hpp
  EditDAO.hpp
  FoodDAO.hpp
  ImageDAO.hpp
  InventoryDAO.hpp
  ReviewDAO.hpp
  UserDAO.hpp
  VendorDAO.hpp
  DAOException.hpp
  DAOException.cpp
)
add_library(entity_dao ${entity_dao_source_files})

target_link_libraries( entity_dao database_dao )
