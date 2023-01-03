package com.github.penguin418;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Test {

    static Session createSession() throws JMSException {
        ConnectionFactory connectionFactory =  new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    static void produceMessage(String msg) throws JMSException {
        Session session = createSession();
        Queue queue = session.createQueue("q");

        Message textMessage = session.createTextMessage(msg);
        session.createProducer(queue).send(textMessage);
        session.close();
    }

    public interface FunctionalInterface {
        public abstract void callback(String text);
    }

    static void consumeMessage(FunctionalInterface f) throws JMSException {
        Session session = createSession();
        Queue queue = session.createQueue("q");
        TextMessage textMessage = (TextMessage) session.createConsumer(queue).receive();
        f.callback(textMessage.getText());
        session.close();
    }

    public static void main(String[] args) throws Exception {
        String message = "hello";

        System.out.println("sent: " + message);
        new Thread(()->{

            try {
                produceMessage("hello");
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{

            try {
                consumeMessage(text -> {
                    System.out.println("received:" + text);
                });
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }).start();
    }


}
