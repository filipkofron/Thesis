set(handler_source_files
  Handler.hpp
  Handler.cpp

  LoginHandler.hpp
  LoginHandler.cpp

  KeepAliveHandler.hpp
  KeepAliveHandler.cpp

  GetInventoryHandler.hpp
  GetInventoryHandler.cpp

  GetFoodDetailHandler.hpp
  GetFoodDetailHandler.cpp

  GetFoodItemHandler.hpp
  GetFoodItemHandler.cpp

  EditInventoryHandler.hpp
  EditInventoryHandler.cpp

  DeleteInventoryHandler.hpp
  DeleteInventoryHandler.cpp

  EditFoodHandler.hpp
  EditFoodHandler.cpp

  GetFoodBaseHandler.hpp
  GetFoodBaseHandler.cpp

  NullHandler.hpp
  NullHandler.cpp

  AddImageHandler.hpp
  AddImageHandler.cpp

  DeleteImageHandler.hpp
  DeleteImageHandler.cpp

  SetUserRatingHandler.hpp
  SetUserRatingHandler.cpp

  HandlerException.hpp
  HandlerException.cpp
)

add_library(handler ${handler_source_files})

target_link_libraries( handler entity_dao )
