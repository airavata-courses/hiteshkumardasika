package entry;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCClient {
    private Connection connection, connection_1;
    private Channel channel, channel_1;
    private String requestQueueName = "rpc_queue", requestQueueName_1 = "rpc_queue_1";
    private String replyQueueName, replyQueueName_1;

    public RPCClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");

        connection = factory.newConnection();
        channel = connection.createChannel();

        replyQueueName = channel.queueDeclare().getQueue();

        //For new Connection
        ConnectionFactory factory_1 = new ConnectionFactory();
        factory_1.setHost("127.0.0.1");

        connection_1 = factory.newConnection();
        channel_1 = connection_1.createChannel();

        replyQueueName_1 = channel_1.queueDeclare().getQueue();
    }

    public String call(String payload, String purpose) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();
        payload = purpose + "-" + payload;
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, payload.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

        channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(corrId)) {
                    response.offer(new String(body, "UTF-8"));
                }
            }
        });

        return response.take();
    }

    public String callForOtherOperations(String payload, String purpose) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();
        payload = purpose + "-" + payload;
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName_1)
                .build();

        channel_1.basicPublish("", requestQueueName_1, props, payload.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<String>(1);

        channel_1.basicConsume(replyQueueName_1, true, new DefaultConsumer(channel_1) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(corrId)) {
                    response.offer(new String(body, "UTF-8"));
                }
            }
        });

        return response.take();
    }


    public void close() throws IOException {
        connection.close();
    }

    //...

}
