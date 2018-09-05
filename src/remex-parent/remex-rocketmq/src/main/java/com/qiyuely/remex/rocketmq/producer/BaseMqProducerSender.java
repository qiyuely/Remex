package com.qiyuely.remex.rocketmq.producer;

import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;

import com.qiyuely.remex.rocketmq.exception.RemexMqException;

/**
 * mq生产者发送器
 * 
 * @author Qiaoxin.Hong
 *
 */
public class BaseMqProducerSender {
	
	/** mq生产者 */
	protected MQProducer producer;
	
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

	public MQProducer getProducer() {
		return producer;
	}

	public void setProducer(MQProducer producer) {
		this.producer = producer;
	}
}
