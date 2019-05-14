package com.xiaochen.rabbitmq.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author shkstart
 * @create 2019-05-11 14:29
 */
public class Consumer {
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

        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.save";
        String queueName = "test_ack_queue";
        //4.声明交换机和队列，并进行绑定，最后制定路由Key
        channel.exchangeDeclare(exchangeName,"direct",true);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        //1.做限流，autoAck一定要设置为:false 关闭消息的自动签收
        //2.channel.basicQos();
        //prefetchSize:对消息的大小就行限制   prefetchCount：最多处理多少条消息，完成之后才能再进去 一般为 1
        //global：应用范围true：应用于channel   false：应用于消费者
        //channel.basicQos(0,1,false);

        channel.basicConsume(queueName,false,new MyConsumer(channel));

    }
}
