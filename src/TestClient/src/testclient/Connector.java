/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testclient;

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
                    sleep(THREAD_SLEEP);
                } catch (InterruptedException ex) {
                }
                onCheck();
            }
        }
        
    }
    
    private ConnectorThread connectorThread = new ConnectorThread();
    private Connection connection;

    public Connector() {
    }
    
    public void start()
    {
        connectorThread.start();
    }
    
    private Connection createConnection()
    {
        System.out.println("Connecting...");
        Connection newConnection = new Connection("127.0.0.1", 4040);
        newConnection.addConnectionListener(Instance.communicator);
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
        }
    }
}
