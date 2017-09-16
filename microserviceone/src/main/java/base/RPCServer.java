package base;

import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Repository
public class RPCServer {

    @Autowired
    CRUDService crudService =new CRUDService();

    private static final String RPC_QUEUE_NAME = "rpc_queue_1";

    public void startServer() {
        System.out.println("***************************CALLLL**************");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");

        Connection connection = null;
        try {
            connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                            .Builder()
                            .correlationId(properties.getCorrelationId())
                            .build();

                    String response = "";

                    try {
                        String message = new String(body, "UTF-8");
                        String purpose = message.substring(0, message.indexOf('-'));
                        if (purpose.compareTo("delete") == 0) {
                            System.out.println("In if with matter " + message.substring(message.indexOf('-') + 1));
                            crudService.deleteFromList(message.substring(message.indexOf('-') + 1));
                        } else {
                            System.out.println("In else with matter " + message.substring(message.indexOf('-') + 1));
                            crudService.searchForTodoList(message.substring(message.indexOf('-') + 1));
                        }

                    } catch (RuntimeException e) {
                        System.out.println(" [.] " + e.toString());
                    } finally {
                        channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));

                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
