package fr.palmus.plugin.websockets;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@ClientEndpoint
public class WebsocketClientEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint remote : " + session.getBasicRemote());
        try {

            //en bref ça va envoyer un message au websocket server du site
            session.getBasicRemote().sendText("WSHH");
        } catch (IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnMessage
    public void onMessage(byte[] message) {
        System.out.println("Received message byte Array : " + Arrays.toString(message));
        String translated = new String(message);
        System.out.println(translated);
        String[] info = translated.split(",");
        System.out.println("Joueur: " +info[0] + " Grade: " + info[1]);

        //ajuste a tes besoins, ça va tout stop quand il a recu son message et a éxécuté ce que t'as fait la haut
        Client.messageLatch.countDown();
    }

    @OnMessage
    public void processMessage(String message) {
        System.out.println("Received message: " + message);
        String[] info = message.split(",");
        System.out.println("Joueur: " +info[0] + " Grade: " + info[1]);
        Client.messageLatch.countDown();
    }


    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }
}