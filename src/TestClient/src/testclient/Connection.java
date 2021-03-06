/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testclient;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kofee
 */
public class Connection {
    private Socket socket;
    private final String addr;
    private final int port;
    private final static int TIMEOUT = 30000;
    private final List<ConnectionListener> connectionListeners;

    public Connection(String addr, int port) {
        this.connectionListeners = new ArrayList<ConnectionListener>();
        this.addr = addr;
        this.port = port;
    }
    
    public void connect() throws IOException
    {
        socket = new Socket(addr, port);
        socket.setSoTimeout(TIMEOUT);
        
        synchronized(connectionListeners)
        {
            for(ConnectionListener connectionListener : connectionListeners)
            {
                connectionListener.onConnected(this);
            }
        }
    }
    
    public void disconnect()
    {
        if(socket != null)
        {
            try
            {
                socket.close();
            }catch(IOException e)
            {
            }
        }
        socket = null;
        
        synchronized(connectionListeners)
        {
            for(ConnectionListener connectionListener : connectionListeners)
            {
                connectionListener.onDisconnected(this);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }
    
    public boolean isConnected()
    {
        Socket tempSocket = socket;
        if(tempSocket == null)
        {
            return false;
        }
        
        return tempSocket.isConnected();
    }
    
    public void addConnectionListener(ConnectionListener connectionListener)
    {
        synchronized(connectionListeners)
        {
            connectionListeners.add(connectionListener);
        }
    }
    
    public void removeConnectionListener(ConnectionListener connectionListener)
    {
        synchronized(connectionListeners)
        {
            connectionListeners.remove(connectionListener);
        }
    }
}
