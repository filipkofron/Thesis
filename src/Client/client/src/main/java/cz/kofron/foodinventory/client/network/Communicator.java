package cz.kofron.foodinventory.client.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.kofron.foodinventory.client.protocol.JSONReceiver;
import cz.kofron.foodinventory.client.protocol.JSONSender;
import cz.kofron.foodinventory.client.protocol.message.GetInventoryRequest;
import cz.kofron.foodinventory.client.protocol.message.GetInventoryResponse;
import cz.kofron.foodinventory.client.protocol.message.KeepAlive;
import cz.kofron.foodinventory.client.protocol.message.LoginRequest;
import cz.kofron.foodinventory.client.protocol.message.LoginResponse;
import cz.kofron.foodinventory.client.protocol.message.Message;


/**
 * @author kofee
 */
public class Communicator implements ConnectionListener
{

	private Connection connection;

	private OutputStream os;
	private InputStream is;

	public LoginResponse login(String name, String password) throws IOException
	{
		synchronized (this)
		{
			JSONSender.send(os, new LoginRequest(name, password).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			LoginResponse lr = (LoginResponse) msg;
			System.out.println("LoginResponse: " + lr);
			if (lr != null)
			{
				System.out.println("LoginResponse: success: " + lr.isSuccess() + " message: " + lr.getMessage());
			}
			return lr;
		}
	}

	public GetInventoryResponse getInventory(boolean direct, int id, String foodName, String foodGtin) throws IOException
	{
		synchronized (this)
		{
			JSONSender.send(os, new GetInventoryRequest(direct, id, foodName, foodGtin).jsonize());
			Message msg = Message.dejsonize(JSONReceiver.receive(is));
			GetInventoryResponse gir = (GetInventoryResponse) msg;
			System.out.println("GetInventoryResponse: " + gir);
			return gir;
		}
	}


	public void keepAlive() throws IOException
	{
		synchronized (this)
		{
			JSONSender.send(os, new KeepAlive().jsonize());
			System.out.println("KeepAlive!");
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

			LoginResponse lr = login("pepa", "zdepa");

			System.out.println("LoginResponse: success: " + lr.isSuccess() + " message: " + lr.getMessage());
		}
		catch (IOException ex)
		{
			Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public void onDisconnected(Connection connection)
	{

	}
}
