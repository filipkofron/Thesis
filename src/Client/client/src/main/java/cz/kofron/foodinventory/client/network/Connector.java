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

// TODO: Auto-generated Javadoc
/**
 * The Class Connector.
 *
 * @author kofee
 */
public class Connector
{
	
	/** The Constant KEEP_ALIVE_PERIOD_MS. */
	private final static int KEEP_ALIVE_PERIOD_MS = 20000;
	
	/** The Constant SERVER_ADDR. */
	public final static String SERVER_ADDR = "foodinventory.halt.cz";
	
	/** The Constant SERVER_PORT. */
	public final static int SERVER_PORT = 4040;
	
	/** The connector thread. */
	private ConnectorThread connectorThread = new ConnectorThread();
	
	/** The connection. */
	private Connection connection;
	
	/** The last keep alive. */
	private long lastKeepAlive = 0;
	
	/**
	 * Instantiates a new connector.
	 */
	public Connector()
	{
	}

	/**
	 * Start.
	 */
	public void start()
	{
		connectorThread.stopped = false;
		connectorThread.start();
	}

	/**
	 * Creates the connection.
	 *
	 * @return the connection
	 */
	private Connection createConnection()
	{
		Connection newConnection = new Connection(SERVER_ADDR, SERVER_PORT, true);
		newConnection.addConnectionListener(NetworkInstance.communicator);
		return newConnection;
	}

	/**
	 * On check.
	 */
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
						if(ConnectionDialogManager.hideDialog())
						{
							ConnectionDialogManager.reloadActivity();
						}
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
	
	/**
	 * Force check.
	 */
	public synchronized void forceCheck()
	{
		connectorThread.check();
	}

	/**
	 * Stop.
	 */
	public void stop()
	{
		connectorThread.safeStop();
	}

	/**
	 * The Class ConnectorThread.
	 */
	private class ConnectorThread extends Thread
	{
		
		/** The Constant THREAD_SLEEP. */
		private final static int THREAD_SLEEP = 3000;
		
		/** The stopped. */
		public boolean stopped;

		/**
		 * Instantiates a new connector thread.
		 */
		public ConnectorThread()
		{
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
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

		/**
		 * Check.
		 */
		public void check()
		{
			synchronized (this)
			{
				notify();
			}
		}

		/**
		 * Safe stop.
		 */
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
