/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.json.simple.JSONObject;

/**
 *
 * @author kofee
 */
public class JSONSender {
    
    private final static Charset utf8Charser = Charset.forName("UTF-8");
    
    public static void send(OutputStream os, JSONObject obj) throws IOException
    {
        String jsonString = obj.toJSONString();
        System.out.println("jsonString: " + jsonString);
        byte [] msgBytes = jsonString.getBytes(utf8Charser);
        int size = msgBytes.length;
        byte [] bytes = new byte[4];

        Atomics.integerToBytes(size, bytes);

        os.write(bytes);
        os.write(msgBytes);
        os.flush();
    }
}
