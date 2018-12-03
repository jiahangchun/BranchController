package com.jiahangchun.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.tools.admin.DefaultMQAdminExt;
import org.apache.rocketmq.common.protocol.body.TopicList;

/**
 * @author chunchun
 * @descritpion
 * @date Created at 2018/10/29 上午9:36
 **/
public class MQAdminExtTest {
    public static void main(String[] args) throws Exception {
        DefaultMQAdminExt defaultMQAdminExt = new DefaultMQAdminExt();
        defaultMQAdminExt.setInstanceName(Long.toString(System.currentTimeMillis()));
        defaultMQAdminExt.setNamesrvAddr(SimpleMqProducor.genHostAndPort("daily.mq.mockuai.com", "9876"));
        defaultMQAdminExt.start();
        com.alibaba.rocketmq.common.protocol.body.TopicList topicList = defaultMQAdminExt.fetchAllTopicList();
        System.out.println(JSON.toJSONString(topicList));

        QueryResult queryResult= defaultMQAdminExt.queryMessage("trade","20966",64,0,System.currentTimeMillis());
        for(MessageExt ext:queryResult.getMessageList()){
            byte[] body=ext.getBody();
            String s=new String(body);
            System.out.println(s);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                defaultMQAdminExt.shutdown();
            }
        }));
    }

}
