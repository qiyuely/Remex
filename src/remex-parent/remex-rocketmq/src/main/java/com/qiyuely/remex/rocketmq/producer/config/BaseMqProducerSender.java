package com.qiyuely.remex.rocketmq.producer.config;

import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;

import com.qiyuely.remex.rocketmq.exception.RemexMqException;

/**
 * mq生产者发送器
 * 
 * @author Qiaoxin.Hong
 *
 */
public abstract class BaseMqProducerSender {
	
	/**
	 * 取得mq生产者
	 * @return
	 */
	protected abstract MQProducer getProducer();
	
	/**
	 * 发送
	 * @param message
	 */
	public void sendMessage(Message message) {
		try {
			getProducer().send(message);
		} catch (Exception e) {
			throw new RemexMqException("Mq producer send error!", e);
		}
	}
}
