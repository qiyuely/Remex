package com.qiyuely.remex.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.qiyuely.remex.rocketmq.exception.RemexMqException;
import com.qiyuely.remex.utils.ValidateUtils;

/**
 * mq消费者配置基础类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class BaseMqConsumerConfig {

	/** mq地址 */
	protected String namesrvAddr;
	
	/** mq消费组 */
	protected String consumerGroup;
	
	/** mq消费主题 */
	protected String topic;
	
	/** mq tag */
	protected String tag;
	
	/** mq消费最小线程数 */
	protected int consumeThreadMin = 20;
	
	/** mq消费最大线程数 */
	protected int consumeThreadMax = 64;
	
	/**
	 * 以默认方式创建mq消费者实现，并注入监听器
	 * @return
	 */
	protected DefaultMQPushConsumer createConsumer(MessageListenerConcurrently...listenerArr) {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
		consumer.setNamesrvAddr(namesrvAddr);
		consumer.setConsumeThreadMin(consumeThreadMin);
		consumer.setConsumeThreadMax(consumeThreadMax);
		
		if (ValidateUtils.isNotEmpty(listenerArr)) {
			//注入监听器
			for (MessageListenerConcurrently listener : listenerArr) {
				consumer.registerMessageListener(listener);
			}
		}
		
		return consumer;
	}
	
	/**
	 * 以默认方式启动消费者
	 */
	protected void start(DefaultMQPushConsumer consumer) {
		try {
			consumer.subscribe(topic, tag);
			consumer.start();
		} catch (Exception e) {
			throw new RemexMqException("Mq consumer start error!", e);
		}
	}
	
	
	
	

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public String getConsumerGroup() {
		return consumerGroup;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getConsumeThreadMin() {
		return consumeThreadMin;
	}

	public void setConsumeThreadMin(int consumeThreadMin) {
		this.consumeThreadMin = consumeThreadMin;
	}

	public int getConsumeThreadMax() {
		return consumeThreadMax;
	}

	public void setConsumeThreadMax(int consumeThreadMax) {
		this.consumeThreadMax = consumeThreadMax;
	}
}
