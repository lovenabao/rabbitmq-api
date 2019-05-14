package com.xiaochen.rabbitmq.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author shkstart
 * @create 2019-05-11 14:24
 */
public class producer {
    public static void main(String[] args) throws Exception{
        //1.创建connectionFactory
        ConnectionFactory connectionFactory =new ConnectionFactory();
        connectionFactory.setHost("120.78.4.81");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.获得connection链接
        Connection connection = connectionFactory.newConnection();

        //3.通过connection创建一个新的channel
        Channel channel = connection.createChannel();
        //声明
        String exchangeName = "test_dlx_exchange";//交换机名称
        String routingKey= "dlx.save";//路由键
        String message = "Hello RabbirMQ DLX Message ....";//发送的消息
        //5.发送一条消息
        for (int i = 0; i < 1; i++) {
            AMQP.BasicProperties properties=new AMQP.BasicProperties().builder().deliveryMode(2).contentEncoding("UTF-8").expiration("10000").build();
            channel.basicPublish(exchangeName,routingKey,true,properties,message.getBytes());

        }



    }
}
