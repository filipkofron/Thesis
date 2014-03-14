/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.protocol.message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author kofee
 */
public class LoginRequest extends Message
{

	private String username;
	private String password;

	public LoginRequest()
	{
	}

	public LoginRequest(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String getHeader()
	{
		return "LoginRequest";
	}

	@Override
	public Message newMessage()
	{
		return new LoginRequest();
	}

	@Override
	protected void dejsonizeContent(JSONObject obj) throws JSONException
	{
		username = obj.getString("username");
		password = obj.getString("password");
	}

	@Override
	protected JSONObject jsonizeContent() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj.put("username", username);
		obj.put("password", password);

		return obj;
	}

}
