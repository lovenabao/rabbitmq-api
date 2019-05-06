package com.xiaochen.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author xiaochen
 * @create 2019-05-06 10:07
 */
public class Procuder {

    public static void main(String[] args) throws Exception {
        //1.创建一个ConnectionFactory,并进行配置
        ConnectionFactory connectionFactory =new ConnectionFactory();
        connectionFactory.setHost("120.78.4.81");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //2，通过连接工厂，创建一个真实的连接
        Connection connection = connectionFactory.newConnection();
        //3.通过connection，创建一个Channel
        Channel channel = connection.createChannel();
        //4.通过channel发送数据
        for (int i = 0; i < 5; i++) {
            String meg="Hello RabbitMQ!";
            channel.basicPublish("","test001",null,meg.getBytes());
        }
        //关闭相关的来连接
        channel.close();
        connection.close();
    }

}
