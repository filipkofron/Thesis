/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.protocol.message;

/**
 * @author kofee
 */
public class MessageInitializer
{
	public static void initialize()
	{
		Message.registerMessageChild(new KeepAlive());

		Message.registerMessageChild(new LoginRequest());
		Message.registerMessageChild(new LoginResponse());

		Message.registerMessageChild(new GetInventoryRequest());
		Message.registerMessageChild(new GetInventoryResponse());

		Message.registerMessageChild(new DeleteInventoryRequest());
		Message.registerMessageChild(new DeleteInventoryResponse());

		Message.registerMessageChild(new EditInventoryRequest());
		Message.registerMessageChild(new EditInventoryResponse());

		Message.registerMessageChild(new GetFoodItemRequest());
		Message.registerMessageChild(new GetFoodItemResponse());

		Message.registerMessageChild(new GetFoodDetailRequest());
		Message.registerMessageChild(new GetFoodDetailResponse());

		Message.registerMessageChild(new EditFoodRequest());
		Message.registerMessageChild(new EditFoodResponse());

		Message.registerMessageChild(new GetFoodBaseRequest());
		Message.registerMessageChild(new GetFoodBaseResponse());

		Message.registerMessageChild(new AddImageRequest());
		Message.registerMessageChild(new AddImageResponse());

		Message.registerMessageChild(new DeleteImageRequest());
		Message.registerMessageChild(new DeleteImageResponse());

		Message.registerMessageChild(new SetUserReviewRequest());
		Message.registerMessageChild(new SetUserReviewResponse());
	}
}
