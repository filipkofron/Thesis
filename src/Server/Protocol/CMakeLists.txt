set(protocol_source_files
  Message.hpp
  Message.cpp

  LoginResponse.hpp
  LoginResponse.cpp
  LoginRequest.hpp
  LoginRequest.cpp

  GetInventoryRequest.hpp
  GetInventoryRequest.cpp
  GetInventoryResponse.hpp
  GetInventoryResponse.cpp

  GetFoodDetailRequest.hpp
  GetFoodDetailRequest.cpp
  GetFoodDetailResponse.hpp
  GetFoodDetailResponse.cpp

  GetFoodItemRequest.hpp
  GetFoodItemRequest.cpp
  GetFoodItemResponse.hpp
  GetFoodItemResponse.cpp

  EditInventoryRequest.hpp
  EditInventoryRequest.cpp
  EditInventoryResponse.hpp
  EditInventoryResponse.cpp

  DeleteInventoryRequest.hpp
  DeleteInventoryRequest.cpp
  DeleteInventoryResponse.hpp
  DeleteInventoryResponse.cpp

  EditFoodRequest.hpp
  EditFoodRequest.cpp
  EditFoodResponse.hpp
  EditFoodResponse.cpp

  GetFoodBaseRequest.hpp
  GetFoodBaseRequest.cpp
  GetFoodBaseResponse.hpp
  GetFoodBaseResponse.cpp

  KeepAlive.hpp
  KeepAlive.cpp
)
add_library(protocol ${protocol_source_files})
