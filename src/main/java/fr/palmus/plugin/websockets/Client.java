package fr.palmus.plugin.websockets;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {


    final static CountDownLatch messageLatch = new CountDownLatch(1);

    public static void LaunchSocket() throws UnknownHostException, ConnectException {

        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            String uri = "ws://localhost:3000";
            System.out.println("Connecting to " + uri);
            //Connexion
            container.connectToServer(WebsocketClientEndpoint.class, URI.create(uri));
            System.out.println("Connected !");
            //Permet l'éxecution de multithreading et l'attente de requete (tu mets 1 sec il va arreter de recevoir des messages et le programme s'arrete)

            //gère comme tu le souhaites
            messageLatch.await(100, TimeUnit.SECONDS);
        } catch (InterruptedException | IOException | DeploymentException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
