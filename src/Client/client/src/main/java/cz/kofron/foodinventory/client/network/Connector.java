/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.network;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import cz.kofron.foodinventory.client.dialog.ConnectionDialogManager;

/**
 * @author kofee
 */
public class Connector
{
	private final static int KEEP_ALIVE_PERIOD_MS = 20000;
	public final static String SERVER_ADDR = "foodinventory.halt.cz";
	public final static int SERVER_PORT = 4040;
	private ConnectorThread connectorThread = new ConnectorThread();
	private Connection connection;
	private long lastKeepAlive = 0;
	public Connector()
	{
	}

	public void start()
	{
		connectorThread.stopped = false;
		connectorThread.start();
	}

	private Connection createConnection()
	{
		Connection newConnection = new Connection(SERVER_ADDR, SERVER_PORT, true);
		newConnection.addConnectionListener(NetworkInstance.communicator);
		return newConnection;
	}

	private void onCheck()
	{
		if (connection == null)
		{
			connection = createConnection();
			try
			{
				connection.connect();
			}
			catch (IOException ex)
			{
				Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else
		{
			if (!connection.isConnected())
			{
				connection.disconnect();
				connection = createConnection();
				try
				{
					connection.connect();
				}
				catch (IOException ex)
				{
					Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			else
			{
				if ((System.currentTimeMillis() - lastKeepAlive) > KEEP_ALIVE_PERIOD_MS)
				{
					if(NetworkInstance.communicator.keepAlive())
					{
						lastKeepAlive = System.currentTimeMillis();
					}
					else
					{
						try
						{
							connection.disconnect();
							connection = null;
						}
						catch (Exception e2)
						{
							e2.printStackTrace();
						}
						onCheck(); // Try connect again
					}
				}
			}
		}
	}
	public synchronized void forceCheck()
	{
		connectorThread.check();
	}

	public void stop()
	{
		connectorThread.safeStop();
	}

	private class ConnectorThread extends Thread
	{
		private final static int THREAD_SLEEP = 3000;
		public boolean stopped;

		public ConnectorThread()
		{
		}

		@Override
		public void run()
		{
			while (!stopped)
			{
				onCheck();
				try
				{
					synchronized (this)
					{
						wait(THREAD_SLEEP);
					}
				}
				catch (InterruptedException ex)
				{

				}
			}
		}

		public void check()
		{
			synchronized (this)
			{
				notify();
			}
		}

		public void safeStop()
		{
			synchronized (this)
			{
				notify();
				stopped = true;
			}
		}
	}
}
