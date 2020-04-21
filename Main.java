package Main;

import model.Consumidor;
import Servicios.Websocket;
import org.eclipse.jetty.websocket.api.Session;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.staticFiles;
import static spark.Spark.webSocket;

public class Main {

    public static List<Session> sessions = new ArrayList<>();

    public static void main(String[] args) throws JMSException {

        staticFiles.location("/dist");
        ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();

        String cola = "notificaciones_sensores.cola";
		
        webSocket("/dataSocket", Websocket.class);


        get("/", (req, res) -> {
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "index");

        },engine);

        Consumidor consumidor = new Consumidor(cola);
		
        consumidor.conectar();


    }

    public static void enviarMensaje(String m) {
		
        for (Session s : sessions) {
			
            try {
				
                s.getRemote().sendString(m);
				
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}