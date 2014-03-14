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
	public static Communicator communicator = new Communicator();
	public static Connector connector = new Connector();

	public static void prepare()
	{
		if (connector != null)
		{
			connector.stop();
		}
		connector = new Connector();
	}
}
