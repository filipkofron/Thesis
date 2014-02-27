/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testclient;

/**
 *
 * @author kofee
 */
public interface ConnectionListener {
    public void onConnected(Connection connection);
    public void onDisconnected(Connection connection);
}
