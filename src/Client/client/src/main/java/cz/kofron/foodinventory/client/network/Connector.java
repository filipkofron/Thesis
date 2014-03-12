/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.network;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kofee
 */
public class Connector {
    private class ConnectorThread extends Thread
    {
        public boolean stopped;
        
        private final static int THREAD_SLEEP = 3000;
        
        public ConnectorThread() {
        }

        @Override
        public void run() {
            while(!stopped)
            {
                try {
	                synchronized (this)
	                {
		                wait(THREAD_SLEEP);
	                }
                } catch (InterruptedException ex) {

                }
                onCheck();
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
    
    private ConnectorThread connectorThread = new ConnectorThread();
    private Connection connection;

	private final static int KEEP_ALIVE_PERIOD_MS = 20000;
	private long lastKeepAlive = 0;

    public Connector() {
    }
    
    public void start()
    {
	    connectorThread.stopped = false;
        connectorThread.start();
    }
    
    private Connection createConnection()
    {
        System.out.println("Connecting...");
        Connection newConnection = new Connection("foodinventory.halt.cz", 4040);
        newConnection.addConnectionListener(NetworkInstance.communicator);
        return newConnection;
    }
    
    private void onCheck()
    {
        if(connection == null)
        {
            connection = createConnection();
            try {
                connection.connect();
            } catch (IOException ex) {
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            if(!connection.isConnected())
            {
                connection.disconnect();
                connection = createConnection();
                try {
                    connection.connect();
                } catch (IOException ex) {
                    Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	        else
            {
	            if((System.currentTimeMillis() - lastKeepAlive) > KEEP_ALIVE_PERIOD_MS)
	            {
		            try
		            {
			            NetworkInstance.communicator.keepAlive();
			            lastKeepAlive = System.currentTimeMillis();
		            }
		            catch (IOException e)
		            {
			            e.printStackTrace();
			            try
			            {
				            connection.disconnect();
				            connection = null;
			            }
			            catch(Exception e2)
			            {
				            e2.printStackTrace();
			            }
			            onCheck(); // Try connect again
		            }
	            }
            }
        }
    }

	public void stop()
	{
		connectorThread.safeStop();
	}
}
