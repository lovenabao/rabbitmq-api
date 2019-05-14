package com.xiaochen.rabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @author shkstart
 * @create 2019-05-10 18:06
 */
public class ProducerConfirm {
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
        String exchangeName = "test_confirm_exchange";//交换机名称
        String routingKey= "confirm.save";//路由键
        String message = "红红火火恍恍惚惚！！！";//发送的消息
        //5.发送一条消息
        channel.basicPublish(exchangeName,routingKey,null,message.getBytes());

        //6.添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("==========YES ACK========");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("==========NO ACK=========");
            }
        });



    }
}
