package Servicios;

import Main.Main;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import org.eclipse.jetty.websocket.api.Session;

@WebSocket
public class Websocket {

    @OnWebSocketConnect
    public void addSession(Session session) {
        System.out.println("Conectando Usuario: " + session.getLocalAddress().getAddress().toString());
        Main.sessions.add(session);
    }

    @OnWebSocketClose
    public void removeSession(Session session, int statusCode, String reason) {
        Main.sessions.remove(session);
    }
}
