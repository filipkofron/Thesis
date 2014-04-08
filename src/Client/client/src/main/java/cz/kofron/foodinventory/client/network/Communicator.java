package cz.kofron.foodinventory.client.network;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.kofron.foodinventory.client.dialog.ConnectionDialogManager;
import cz.kofron.foodinventory.client.model.FoodDetail;
import cz.kofron.foodinventory.client.model.FoodHelper;
import cz.kofron.foodinventory.client.protocol.JSONReceiver;
import cz.kofron.foodinventory.client.protocol.JSONSender;
import cz.kofron.foodinventory.client.protocol.message.AddImageRequest;
import cz.kofron.foodinventory.client.protocol.message.AddImageResponse;
import cz.kofron.foodinventory.client.protocol.message.DeleteImageRequest;
import cz.kofron.foodinventory.client.protocol.message.DeleteImageResponse;
import cz.kofron.foodinventory.client.protocol.message.DeleteInventoryRequest;
import cz.kofron.foodinventory.client.protocol.message.DeleteInventoryResponse;
import cz.kofron.foodinventory.client.protocol.message.EditFoodRequest;
import cz.kofron.foodinventory.client.protocol.message.EditFoodResponse;
import cz.kofron.foodinventory.client.protocol.message.EditInventoryRequest;
import cz.kofron.foodinventory.client.protocol.message.EditInventoryResponse;
import cz.kofron.foodinventory.client.protocol.message.GetFoodBaseRequest;
import cz.kofron.foodinventory.client.protocol.message.GetFoodBaseResponse;
import cz.kofron.foodinventory.client.protocol.message.GetFoodDetailRequest;
import cz.kofron.foodinventory.client.protocol.message.GetFoodDetailResponse;
import cz.kofron.foodinventory.client.protocol.message.GetFoodItemRequest;
import cz.kofron.foodinventory.client.protocol.message.GetFoodItemResponse;
import cz.kofron.foodinventory.client.protocol.message.GetInventoryRequest;
import cz.kofron.foodinventory.client.protocol.message.GetInventoryResponse;
import cz.kofron.foodinventory.client.protocol.message.KeepAlive;
import cz.kofron.foodinventory.client.protocol.message.LoginRequest;
import cz.kofron.foodinventory.client.protocol.message.LoginResponse;
import cz.kofron.foodinventory.client.protocol.message.Message;
import cz.kofron.foodinventory.client.protocol.message.SetUserReviewRequest;
import cz.kofron.foodinventory.client.protocol.message.SetUserReviewResponse;


/**
 * @author kofee
 */
public class Communicator implements ConnectionListener
{

	private Connection connection;

	private boolean connected;

	private OutputStream os;
	private InputStream is;

	private String username;

	private boolean showDialog;

	public Communicator(String username, boolean showDialog)
	{
		this.username = username;
		this.showDialog = showDialog;
	}

	public boolean isConnected()
	{
		return connected;
	}

	private void waitForConnection()
	{
		if(!connected)
		{
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public LoginResponse login(String name) throws IOException
	{
		synchronized (this)
		{
			JSONSender.send(os, new LoginRequest(name).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			LoginResponse lr = (LoginResponse) msg;
			return lr;
		}
	}

	public GetInventoryResponse getInventory(boolean direct, int id, String foodName, String foodGtin) throws IOException
	{
		waitForConnection();
		synchronized (this)
		{
			JSONSender.send(os, new GetInventoryRequest(direct, id, foodName, foodGtin).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			GetInventoryResponse gir = (GetInventoryResponse) msg;
			return gir;
		}
	}

	public GetFoodDetailResponse getFoodDetail(int id) throws IOException
	{
		waitForConnection();
		synchronized (this)
		{
			JSONSender.send(os, new GetFoodDetailRequest(id).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			GetFoodDetailResponse gfdr = (GetFoodDetailResponse) msg;
			return gfdr;
		}
	}

	public boolean deleteInventory(int id) throws IOException
	{
		waitForConnection();
		synchronized (this)
		{
			JSONSender.send(os, new DeleteInventoryRequest(id).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			DeleteInventoryResponse dir = (DeleteInventoryResponse) msg;
			return dir.isSuccess();
		}
	}

	public boolean editFoodItem(boolean adding, int id, int foodId, long useBy, int count) throws IOException
	{
		waitForConnection();
		synchronized (this)
		{
			JSONSender.send(os, new EditInventoryRequest(adding, id, foodId, useBy, count).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			EditInventoryResponse eir = (EditInventoryResponse) msg;
			return eir.isSuccess();
		}
	}

	public GetFoodItemResponse getFoodItem(boolean direct, int id, String name, String gtin, int skip) throws IOException
	{
		waitForConnection();
		synchronized (this)
		{
			JSONSender.send(os, new GetFoodItemRequest(direct, id, name, gtin, skip).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			GetFoodItemResponse gfir = (GetFoodItemResponse) msg;
			return gfir;
		}
	}

	public GetFoodBaseResponse getFoodBase() throws IOException
	{
		synchronized (this)
		{
			JSONSender.send(os, new GetFoodBaseRequest().jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			GetFoodBaseResponse gfbs = (GetFoodBaseResponse) msg;
			return gfbs;
		}
	}

	public EditFoodResponse editFood(boolean adding, int id, String name, String vendor, int categoryId, String gtin, String description, long defaultUseBy, int amountType, float amount, float usualPrice) throws IOException
	{
		waitForConnection();
		synchronized (this)
		{
			JSONSender.send(os, new EditFoodRequest(adding, id, name, vendor, categoryId, gtin, description, defaultUseBy, amountType, amount, usualPrice).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			EditFoodResponse efr = (EditFoodResponse) msg;
			return efr;
		}
	}

	public boolean addImage(Bitmap bitmap, int id) throws IOException
	{
		waitForConnection();
		synchronized (this)
		{
			JSONSender.send(os, new AddImageRequest(bitmap, id).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			AddImageResponse air = (AddImageResponse) msg;
			return air.isSuccess();
		}
	}

	public boolean deleteImage(int id) throws IOException
	{
		waitForConnection();
		synchronized (this)
		{
			JSONSender.send(os, new DeleteImageRequest(id).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			DeleteImageResponse dir = (DeleteImageResponse) msg;
			return dir.isSuccess();
		}
	}

	public boolean setUserReview(float rating, boolean delete, int foodId, String text) throws IOException
	{
		waitForConnection();
		synchronized (this)
		{
			JSONSender.send(os, new SetUserReviewRequest(rating, delete, foodId, text).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			SetUserReviewResponse surr = (SetUserReviewResponse) msg;
			return surr.isSuccess();
		}
	}

	public boolean keepAlive()
	{
		synchronized (this)
		{
			try
			{
				JSONSender.send(os, new KeepAlive().jsonize());
				KeepAlive result = (KeepAlive) Message.dejsonize(JSONReceiver.receive(is));
			}
			catch(Exception e)
			{
				return false;
			}
			return true;
		}
	}

	@Override
	public void onConnected(Connection connection)
	{
		try
		{
			this.connection = connection;
			os = connection.getSocket().getOutputStream();
			is = connection.getSocket().getInputStream();

			LoginResponse lr = login(username);

			System.out.println("LoginResponse: success: " + lr.isSuccess() + " message: " + lr.getMessage());

			GetFoodBaseResponse gfbr = getFoodBase();

			FoodHelper.vendors = gfbr.getVendors();
			FoodHelper.categories = gfbr.getCategories();

			if(showDialog)
			{
				ConnectionDialogManager.hideDialog();
			}
			ConnectionDialogManager.reloadActivity();
		}
		catch (IOException ex)
		{
			Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
		}

		connected = true;
	}

	@Override
	public void onDisconnected(Connection connection)
	{
		connected = false;
	}

	public String getUsername()
	{
		return username;
	}
}
