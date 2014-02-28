package testclient;

import protocol.MessageInitializer;

/**
 *
 * @author kofee
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MessageInitializer.initialize();
        Connector connector = new Connector();
        connector.start();
    }
}
