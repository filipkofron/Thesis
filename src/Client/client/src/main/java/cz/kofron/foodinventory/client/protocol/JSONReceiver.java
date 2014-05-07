/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.protocol;

import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.Charset;

import cz.kofron.foodinventory.client.network.NetworkInstance;
import cz.kofron.foodinventory.client.util.Atomics;

// TODO: Auto-generated Javadoc
/**
 * The Class JSONReceiver.
 *
 * @author kofee
 */
public class JSONReceiver
{

	/** The Constant utf8Charser. */
	private final static Charset utf8Charser = Charset.forName("UTF-8");

	/**
	 * Receive.
	 *
	 * @param is the is
	 * @return the JSON object
	 */
	public static JSONObject receive(InputStream is)
	{
		try
		{
			String str;

			byte[] bytes = new byte[4];

			BufferReader.readFully(bytes, bytes.length, is);

			int size = Atomics.integerFromBytes(bytes);

			bytes = new byte[size];
			BufferReader.readFully(bytes, size, is);

			str = new String(bytes, utf8Charser);

			JSONObject obj = new JSONObject(str);
			return obj;
		}
		catch (Exception e)
		{
			NetworkInstance.connector.forceCheck();
			e.printStackTrace();
		}
		return null;
	}
}
