package testclient;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author kofee
 */
public class Communicator implements ConnectionListener {

    private final static Charset utf8Charser = Charset.forName("UTF-8");
    
    @Override
    public void onConnected(Connection connection) {
        try {
            OutputStream os = connection.getSocket().getOutputStream();
            
            JSONObject obj = new JSONObject();
            obj.put("A key", "Some value");

            JSONObject obj2 = new JSONObject();
            obj2.put("A sub key", "Some sub value");
            
            obj.put("A key", "Some value");
            obj.put("A key 2", obj2);

            //String helloWorld = obj.toJSONString();
            String helloWorld = "49ut89ehrdgiojpz4]gj";
            byte [] msgBytes = helloWorld.getBytes(utf8Charser);
            int size = msgBytes.length;
            byte [] bytes = new byte[4];
            
            bytes[0] = (byte)((size >> 24) & 0xFF);
            bytes[1] = (byte)((size >> 16) & 0xFF);
            bytes[2] = (byte)((size >> 8) & 0xFF);
            bytes[3] = (byte)(size & 0xFF);
            
            os.write(bytes);
            os.write(msgBytes);
            os.flush();
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
