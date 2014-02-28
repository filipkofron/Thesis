package protocol;

import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 *
 * @author kofee
 */
public abstract class Message {

    private final static Map<String, Message> messageChilds = new HashMap<String, Message>();

    public abstract String getHeader();
    public abstract Message newMessage();
    protected abstract void dejsonizeContent(JSONObject obj);
    protected abstract JSONObject jsonizeContent();

    public static void registerMessageChild(Message message) {
        messageChilds.put(message.getHeader(), message);
    }

    public final static Message dejsonize(JSONObject obj)
    {
        try
        {
            String header = (String) obj.get("header");
            Message factoryMessage = messageChilds.get(header);
            Message message = factoryMessage.newMessage();
            message.dejsonizeContent((JSONObject) obj.get("content"));
            return message;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public final JSONObject jsonize()
    {
        try
        {
            JSONObject obj = new JSONObject();
            obj.put("header", getHeader());
            obj.put("content", jsonizeContent());
            return obj;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
