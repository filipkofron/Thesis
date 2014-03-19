/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.network;

/**
 * @author kofee
 */
public class NetworkInstance
{
	public static Communicator communicator;
	public static Connector connector = new Connector();

	private static boolean prepared = false;

	public static void prepare(String username)
	{
		if (connector != null)
		{
			connector.stop();
		}
		connector = new Connector();
		communicator = new Communicator(username);
		prepared = true;
	}

	public static boolean isPrepared()
	{
		return prepared;
	}
}
