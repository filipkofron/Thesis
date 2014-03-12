/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.kofron.foodinventory.client.protocol;

/**
 *
 * @author kofee
 */
public class MessageInitializer {
    public static void initialize()
    {
        Message.registerMessageChild(new LoginRequest());
        Message.registerMessageChild(new LoginResponse());
    }
}
