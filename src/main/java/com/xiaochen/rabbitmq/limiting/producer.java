package com.xiaochen.rabbitmq.limiting;

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

        //4.指定我们的消息投递模式：消息的确认模式
        channel.confirmSelect();
        //声明
        String exchangeName = "test_qos_exchange";//交换机名称
        String routingKey= "qos.save";//路由键
        String message = "红红火火恍恍惚惚！！！";//发送的消息
        //5.发送一条消息
        for (int i = 0; i < 10; i++) {
            channel.basicPublish(exchangeName,routingKey,true,null,message.getBytes());

        }



    }
}
