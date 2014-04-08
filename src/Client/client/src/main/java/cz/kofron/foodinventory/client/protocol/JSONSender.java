/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.protocol;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.util.Atomics;

/**
 * @author kofee
 */
public class JSONSender
{

	private final static Charset utf8Charser = Charset.forName("UTF-8");

	public static void send(OutputStream os, JSONObject obj) throws IOException
	{
		String jsonString = obj.toString();
		byte[] msgBytes = jsonString.getBytes(utf8Charser);
		int size = msgBytes.length;
		byte[] bytes = new byte[4];

		Atomics.integerToBytes(size, bytes);

		try
		{
			os.write(bytes);
			os.write(msgBytes);
			os.flush();
		}
		catch (NullPointerException e)
		{
			NetworkInstance.connector.forceCheck();
			throw new IOException(e);
		}
	}
}
