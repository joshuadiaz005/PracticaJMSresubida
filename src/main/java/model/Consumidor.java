package model;

import Main.Main;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Consumidor {

    ActiveMQConnectionFactory factory;
    Connection connection;
    Session session;
    Queue queue;
    Topic topic;
    MessageConsumer consumer;
    String cola;

    public Consumidor(String cola) {
        this.cola = cola;
    }

    /**
     *
     * @throws JMSException
     */
    public void conectar() throws JMSException {

        factory = new ActiveMQConnectionFactory("admin", "admin", "failover:tcp://localhost:61616");
        connection = factory.createConnection();
        connection.start();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue(cola);
        consumer = session.createConsumer(queue);
        consumer.setMessageListener(message -> {

            try {
                TextMessage messageTexto = (TextMessage) message;
                Main.enviarMensaje(messageTexto.getText());
                System.out.println("mensaje: " + messageTexto.getText()+" - "+new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }


    public void cerrarConexion() throws JMSException {
        connection.stop();
        connection.close();
    }

}
