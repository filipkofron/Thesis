/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.network;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving connection events.
 * The class that is interested in processing a connection
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addConnectionListener<code> method. When
 * the connection event occurs, that object's appropriate
 * method is invoked.
 *
 * @author kofee
 */
public interface ConnectionListener
{
	
	/**
	 * On connected.
	 *
	 * @param connection the connection
	 */
	public void onConnected(Connection connection);

	/**
	 * On disconnected.
	 *
	 * @param connection the connection
	 */
	public void onDisconnected(Connection connection);
}
