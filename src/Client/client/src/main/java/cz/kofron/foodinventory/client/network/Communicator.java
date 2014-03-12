package cz.kofron.foodinventory.client.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.kofron.foodinventory.client.protocol.JSONReceiver;
import cz.kofron.foodinventory.client.protocol.JSONSender;
import cz.kofron.foodinventory.client.protocol.KeepAlive;
import cz.kofron.foodinventory.client.protocol.LoginRequest;
import cz.kofron.foodinventory.client.protocol.LoginResponse;
import cz.kofron.foodinventory.client.protocol.Message;


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
			System.out.println("LoginResponse: success: " + lr.isSuccess() + " message: " + lr.getMessage());
			return lr;
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
