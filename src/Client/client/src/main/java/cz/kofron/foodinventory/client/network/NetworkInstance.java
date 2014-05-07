/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.network;

// TODO: Auto-generated Javadoc
/**
 * The Class NetworkInstance.
 *
 * @author kofee
 */
public class NetworkInstance
{
	
	/** The communicator. */
	public static Communicator communicator;
	
	/** The connector. */
	public static Connector connector = new Connector();

	/** The prepared. */
	private static boolean prepared = false;

	/**
	 * Prepare.
	 *
	 * @param username the username
	 */
	public static void prepare(String username)
	{
		if (connector != null)
		{
			connector.stop();
		}
		connector = new Connector();
		communicator = new Communicator(username, true);
		prepared = true;
	}

	/**
	 * Checks if is prepared.
	 *
	 * @return true, if is prepared
	 */
	public static boolean isPrepared()
	{
		return prepared;
	}
}
