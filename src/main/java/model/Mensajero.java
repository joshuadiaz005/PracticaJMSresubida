package model;

import Servicios.Json;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

public class Mensajero {

    public Mensajero() {

    }

    public void enviarMensaje(String cola) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection("admin", "admin");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(cola);
        MessageProducer producer = session.createProducer(queue);
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(2);
                Valores valor = new Valores();
                String mensajeData = Json.toJson(valor);
                TextMessage message = session.createTextMessage(mensajeData);
                producer.send(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
