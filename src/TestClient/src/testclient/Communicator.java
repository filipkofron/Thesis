package testclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocol.JSONReceiver;
import protocol.JSONSender;
import protocol.LoginRequest;
import protocol.LoginResponse;
import protocol.Message;

/**
 *
 * @author kofee
 */
public class Communicator implements ConnectionListener {

    
    
    @Override
    public void onConnected(Connection connection) {
        try {
            OutputStream os = connection.getSocket().getOutputStream();
            InputStream is = connection.getSocket().getInputStream();
            
            JSONSender.send(os, new LoginRequest("pepa", "zdepa").jsonize());
            Message msg = Message.dejsonize(JSONReceiver.receive(is));
            LoginResponse lr = (LoginResponse) msg;
            System.out.println("LoginResponse: success: " + lr.isSuccess() + " message: " + lr.getMessage());
            
            os.close();
            connection.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void onDisconnected(Connection connection) {
        
    }

    
}
