set(entity_source_files
  User.hpp
  User.cpp
  Category.hpp
  Category.cpp
  Edit.hpp
  Edit.cpp
  Food.hpp
  Food.cpp
  Image.hpp
  Image.cpp
  Inventory.hpp
  Inventory.cpp
  Review.hpp
  Review.cpp
  Vendor.hpp
  Vendor.cpp
)

add_library(entity ${entity_source_files})

add_subdirectory(DAO)
