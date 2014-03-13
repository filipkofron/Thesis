set(handler_source_files
  Handler.hpp
  Handler.cpp
  LoginHandler.hpp
  LoginHandler.cpp
  GetInventoryHandler.hpp
  GetInventoryHandler.cpp
  NullHandler.hpp
  NullHandler.cpp
  HandlerException.hpp
  HandlerException.cpp
)

add_library(handler ${handler_source_files})

target_link_libraries( handler entity_dao )
