/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.protocol.message;

import org.json.*;

/**
 *
 * @author kofee
 */
public class LoginResponse extends Message
{
    private boolean success;
    private String message;

    public LoginResponse() {
    }
    
    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public String getHeader() {
        return "LoginResponse";
    }

    @Override
    public Message newMessage() {
        return new LoginResponse();
    }

    @Override
    protected void dejsonizeContent(JSONObject obj) throws JSONException
    {
        success = obj.getBoolean("success");
        message = obj.getString("message");
    }

    @Override
    protected JSONObject jsonizeContent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
