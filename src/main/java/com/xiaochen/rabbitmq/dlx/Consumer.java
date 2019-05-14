package com.xiaochen.rabbitmq.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

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
        //这是一个普通的交换机和队列以及路由
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";
        String queueName = "test_dlx_queue";
        //4.声明交换机和队列，并进行绑定，最后制定路由Key
        channel.exchangeDeclare(exchangeName,"direct",true);

        Map<String,Object> arguments=new HashMap<>();
        arguments.put("x-dead-letter-exchange","dlx.exchange");
        //arguments属性要设置到声明队列上
        channel.queueDeclare(queueName,true,false,false,arguments);
        //绑定交换机和队列
        channel.queueBind(queueName,exchangeName,routingKey);

        //要进行死信队列的声明:s
        channel.exchangeDeclare("dlx.exchange","direct",true,false,null);
        channel.queueDeclare("dlx.queue",true,false,false,null);
        channel.queueBind("dlx.queue","dlx.exchange","#");


        channel.basicConsume(queueName,true,new MyConsumer(channel));

    }
}
