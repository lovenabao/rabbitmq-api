package com.xiaochen.rabbitmq.returnlistener;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author shkstart
 * @create 2019-05-11 11:01
 */
public class ProducerReturnListener {
    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("120.78.4.81");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //声明
        String exchangeName="test_return_exchange";
        String routingKey="test_return_routingKey1";
        String meg="Hello RabbitMQ !!!";

        channel.addReturnListener(new ReturnListener() {
            /**
             *
             * @param replyCode 响应码
             * @param replyText 响应的实体内容
             * @param exchange  交换机
             * @param routingKey 路由键
             * @param properties  properties文件
             * @param body  实体内容
             * @throws IOException
             */
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange,
                                     String routingKey, AMQP.BasicProperties properties,
                                     byte[] body) throws IOException {
                System.out.println("=========handle return=========");
                System.out.println("replyCode:  " + replyCode);
                System.out.println("replyText:  " + replyText);
                System.out.println("exchange:  " + exchange);
                System.out.println("routingKey:  " + routingKey);
                System.out.println("properties:  " + properties);
                System.out.println("body: " + new String(body));

            }
        });
        channel.basicPublish(exchangeName,routingKey,true,null,meg.getBytes());

    }
}
