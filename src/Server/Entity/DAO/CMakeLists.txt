set(entity_dao_source_files
  UserDAO.hpp
  UserDAOImpl.hpp
  UserDAOImpl.cpp
  DAOException.hpp
  DAOException.cpp
)
add_library(entity_dao ${entity_dao_source_files})
