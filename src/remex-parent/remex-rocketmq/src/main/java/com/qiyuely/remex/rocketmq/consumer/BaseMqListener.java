package com.qiyuely.remex.rocketmq.consumer;

import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * mq监听基础类
 * 
 * @author Qiaoxin.Hong
 *
 */
public abstract class BaseMqListener implements MessageListenerConcurrently {

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		for (MessageExt msg : msgs) {
			boolean result = handleMessage(msg);
			if (!result) {
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
	
	/**
     * 处理消息的接口
     * @param messageExt
     * @return
     */
    protected abstract boolean handleMessage(MessageExt messageExt);
}
