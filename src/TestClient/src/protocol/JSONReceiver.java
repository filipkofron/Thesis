/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol;

import java.io.InputStream;
import java.nio.charset.Charset;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author kofee
 */
public class JSONReceiver {
    
    private final static Charset utf8Charser = Charset.forName("UTF-8");
    
    public static JSONObject receive(InputStream is)
    {
        try
        {
            String str;
            
            byte [] bytes = new byte[4];

            BufferReader.readFully(bytes, bytes.length, is);
            
            int size = Atomics.integerFromBytes(bytes);
            
            System.out.println("GOT size: " + size);
            
            bytes = new byte[size];
            BufferReader.readFully(bytes, size, is);
            
            str = new String(bytes, utf8Charser);
            
            System.out.println("GOT str: " + str);
            
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(str);
            return obj;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
