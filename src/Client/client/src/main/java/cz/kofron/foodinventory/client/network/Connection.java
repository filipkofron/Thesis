/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.network;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cz.kofron.foodinventory.client.dialog.ConnectionDialogManager;

// TODO: Auto-generated Javadoc
/**
 * The Class Connection.
 *
 * @author kofee
 */
public class Connection
{
	
	/** The Constant TIMEOUT. */
	private final static int TIMEOUT = 30000;
	
	/** The addr. */
	private final String addr;
	
	/** The port. */
	private final int port;
	
	/** The connection listeners. */
	private final List<ConnectionListener> connectionListeners;
	
	/** The socket. */
	private Socket socket;
	
	/** The show dialog. */
	private boolean showDialog;

	/**
	 * Instantiates a new connection.
	 *
	 * @param addr the addr
	 * @param port the port
	 * @param showDialog the show dialog
	 */
	public Connection(String addr, int port, boolean showDialog)
	{
		this.connectionListeners = new ArrayList<ConnectionListener>();
		this.addr = addr;
		this.port = port;
		this.showDialog = showDialog;
	}

	/**
	 * Connect.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void connect() throws IOException
	{
		if(showDialog)
		{
			ConnectionDialogManager.showDialog();
		}
		socket = new Socket(addr, port);
		socket.setSoTimeout(TIMEOUT);

		synchronized (connectionListeners)
		{
			for (ConnectionListener connectionListener : connectionListeners)
			{
				connectionListener.onConnected(this);
			}
		}
	}

	/**
	 * Disconnect.
	 */
	public void disconnect()
	{
		if (socket != null)
		{
			try
			{
				socket.close();
			}
			catch (IOException e)
			{
			}
		}
		socket = null;

		synchronized (connectionListeners)
		{
			for (ConnectionListener connectionListener : connectionListeners)
			{
				connectionListener.onDisconnected(this);
			}
		}
	}

	/**
	 * Gets the socket.
	 *
	 * @return the socket
	 */
	public Socket getSocket()
	{
		return socket;
	}

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected()
	{
		Socket tempSocket = socket;
		if (tempSocket == null)
		{
			return false;
		}

		return tempSocket.isConnected();
	}

	/**
	 * Adds the connection listener.
	 *
	 * @param connectionListener the connection listener
	 */
	public void addConnectionListener(ConnectionListener connectionListener)
	{
		synchronized (connectionListeners)
		{
			connectionListeners.add(connectionListener);
		}
	}

	/**
	 * Removes the connection listener.
	 *
	 * @param connectionListener the connection listener
	 */
	public void removeConnectionListener(ConnectionListener connectionListener)
	{
		synchronized (connectionListeners)
		{
			connectionListeners.remove(connectionListener);
		}
	}
}
