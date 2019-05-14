package com.xiaochen.rabbitmq.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author shkstart
 * @create 2019-05-11 14:37
 */
public class MyConsumer extends DefaultConsumer {
    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    private Channel channel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("========consumer messaage=========");
        System.out.println("consumerTag : " + consumerTag);
        System.out.println("envelope : " + envelope);
        System.out.println("properties : " + properties);
        System.out.println("body : " + new String(body));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //multiple:设置为true：批量签收，设置为false：不批量签收，看上一级 prefetchSize：是否为 1
        if ((Integer) properties.getHeaders().get("num") == 0) {
            channel.basicNack(envelope.getDeliveryTag(), false, true);//multiple:是否批量处理，requeue:是否重回队列
        }else {
            channel.basicAck(envelope.getDeliveryTag(), false);
        }
    }
}