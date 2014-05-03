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


// TODO: Auto-generated Javadoc
/**
 * The Class Communicator.
 *
 * @author kofee
 */
public class Communicator implements ConnectionListener
{

	/** The connection. */
	private Connection connection;

	/** The connected. */
	private boolean connected;

	/** The os. */
	private OutputStream os;
	
	/** The is. */
	private InputStream is;

	/** The username. */
	private String username;

	/** The show dialog. */
	private boolean showDialog;

	/**
	 * Instantiates a new communicator.
	 *
	 * @param username the username
	 * @param showDialog the show dialog
	 */
	public Communicator(String username, boolean showDialog)
	{
		this.username = username;
		this.showDialog = showDialog;
	}

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected()
	{
		return connected;
	}

	/**
	 * Wait for connection.
	 */
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

	/**
	 * Login.
	 *
	 * @param name the name
	 * @return the login response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Gets the inventory.
	 *
	 * @param direct the direct
	 * @param id the id
	 * @param foodName the food name
	 * @param foodGtin the food gtin
	 * @return the inventory
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Gets the food detail.
	 *
	 * @param id the id
	 * @return the food detail
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Delete inventory.
	 *
	 * @param id the id
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Edits the food item.
	 *
	 * @param adding the adding
	 * @param id the id
	 * @param foodId the food id
	 * @param useBy the use by
	 * @param count the count
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Gets the food item.
	 *
	 * @param direct the direct
	 * @param id the id
	 * @param name the name
	 * @param gtin the gtin
	 * @param skip the skip
	 * @return the food item
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Gets the food base.
	 *
	 * @return the food base
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Edits the food.
	 *
	 * @param adding the adding
	 * @param id the id
	 * @param name the name
	 * @param vendor the vendor
	 * @param categoryId the category id
	 * @param gtin the gtin
	 * @param description the description
	 * @param defaultUseBy the default use by
	 * @param amountType the amount type
	 * @param amount the amount
	 * @param usualPrice the usual price
	 * @return the edits the food response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Adds the image.
	 *
	 * @param bitmap the bitmap
	 * @param id the id
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Delete image.
	 *
	 * @param id the id
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Sets the user review.
	 *
	 * @param rating the rating
	 * @param delete the delete
	 * @param foodId the food id
	 * @param text the text
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Keep alive.
	 *
	 * @return true, if successful
	 */
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

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.network.ConnectionListener#onConnected(cz.kofron.foodinventory.client.network.Connection)
	 */
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

	/* (non-Javadoc)
	 * @see cz.kofron.foodinventory.client.network.ConnectionListener#onDisconnected(cz.kofron.foodinventory.client.network.Connection)
	 */
	@Override
	public void onDisconnected(Connection connection)
	{
		connected = false;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}
}
