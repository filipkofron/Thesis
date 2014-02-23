set(entity_dao_source_files
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
