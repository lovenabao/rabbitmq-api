package com.xiaochen.rabbitmq.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

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

        //4.指定我们的消息投递模式：消息的确认模式
        channel.confirmSelect();
        //声明
        String exchangeName = "test_ack_exchange";//交换机名称
        String routingKey= "ack.save";//路由键
        String message = "红红火火恍恍惚惚！！！";//发送的消息

        //5.发送一条消息
        for (int i = 0; i < 5; i++) {
            Map<String,Object> headers=new HashMap<>();
            headers.put("num",i);
            AMQP.BasicProperties properties=new AMQP.BasicProperties().builder().deliveryMode(2).contentEncoding("UTF-8").headers(headers).build();
            channel.basicPublish(exchangeName,routingKey,true,properties,(message+i).getBytes());

        }



    }
}
