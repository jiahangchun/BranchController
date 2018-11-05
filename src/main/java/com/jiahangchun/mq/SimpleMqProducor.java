package com.jiahangchun.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/10/26 下午2:00
 **/
public class SimpleMqProducor {

    private static final String DEFAULT_HOST = "daily.mq.mockuai.com";
    private static final String DEFAULT_PORT = "9876";
    private static final String DEFAULT_PRODUCER_GROUP_NAME = "example_group_name";

    /**
     * 简单的自我测试
     *
     * @param port
     * @param host
     * @param topic
     * @param tag
     * @param message
     * @return
     */
    public static String/*result*/ sendSimpleMqMessage(String port, String host, String topic, String tag, String message) {
        DefaultMQProducer producer = null;
        StringBuilder sb = new StringBuilder();
        try {
            producer = new DefaultMQProducer(DEFAULT_PRODUCER_GROUP_NAME);
            producer.setNamesrvAddr(genHostAndPort(host, port));
            producer.start();

            Message msg = new Message(topic, tag, (message).getBytes("UTF-8"));
            SendResult sendResult = producer.send(msg);
            sb.append((JSON.toJSONString(sendResult)));
        } catch (Exception e) {
            sb.append(e.getMessage());
        } finally {
            producer.shutdown();
        }
        return sb.toString();
    }

    /**
     * 获取MQ配置地址
     *
     * @param host
     * @param port
     * @return
     */
    public static String genHostAndPort(String host, String port) {
        return host + ":" + port;
    }

    public static void main(String[] args) {
        sendSimpleMqMessage(null,null,"TOPIC","AAA","message");
    }
}
