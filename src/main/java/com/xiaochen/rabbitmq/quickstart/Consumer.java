package com.xiaochen.rabbitmq.quickstart;

import com.rabbitmq.client.*;

/**
 * @author xiaochen
 * @create 2019-05-06 10:08
 */
public class Consumer {

    public static void main(String[] args) throws Exception{
        //1.创建一个ConnectionFactory,并进行配置
        ConnectionFactory connectionFactory =new ConnectionFactory();
        connectionFactory.setHost("120.78.4.81");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //2，通过连接工厂，创建一个真实的连接
        Connection connection = connectionFactory.newConnection();
        //3.通过connection，创建一个Channel
        Channel channel = connection.createChannel();
        //4.声明（创建）一个队列
        channel.queueDeclare("test001",true,false,false,null);
        //5.创建消费者
        QueueingConsumer queueingConsumer =new QueueingConsumer(channel);
        //6.设置 channel
        channel.basicConsume("test001",true,queueingConsumer);
        while (true){
            //7.获取消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String str=new String(delivery.getBody());
            System.out.println("消费端： "+str);
            //Envelope envelope = delivery.getEnvelope();

        }

    }

}
