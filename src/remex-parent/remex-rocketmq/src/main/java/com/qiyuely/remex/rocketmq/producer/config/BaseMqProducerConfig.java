package com.qiyuely.remex.rocketmq.producer.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;

import com.qiyuely.remex.rocketmq.exception.RemexMqException;

/**
 * mq生产者配置基础类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class BaseMqProducerConfig {
	
	/** mq生产组 */
	protected String producerGroup;
	
	/** mq地址 */
	protected String namesrvAddr;
	
	/** mq实例 */
	protected String instanceName;
	
	/** mq消息大小 */
	protected int maxMessageSize = 1024 * 1024 * 4; // 4M;
	
	/** mq发送超时时间 */
	protected int sendMsgTimeout = 3000;
	
	/**
	 * 以默认方式创建mq生产者实现
	 * @return
	 */
	protected DefaultMQProducer createProducer() {
		DefaultMQProducer producer = new DefaultMQProducer(this.producerGroup);
		producer.setNamesrvAddr(namesrvAddr);
		producer.setInstanceName(instanceName);
		producer.setMaxMessageSize(maxMessageSize);
		producer.setSendMsgTimeout(sendMsgTimeout);
		return producer;
	}
	
	/**
	 * 以默认方式启动生产者
	 */
	protected void start(DefaultMQProducer producer) {
		try {
			producer.start();
		} catch (Exception e) {
			throw new RemexMqException("Mq producer start error!", e);
		}
	}
	
	
	

	public String getProducerGroup() {
		return producerGroup;
	}

	public void setProducerGroup(String producerGroup) {
		this.producerGroup = producerGroup;
	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public int getMaxMessageSize() {
		return maxMessageSize;
	}

	public void setMaxMessageSize(int maxMessageSize) {
		this.maxMessageSize = maxMessageSize;
	}

	public int getSendMsgTimeout() {
		return sendMsgTimeout;
	}

	public void setSendMsgTimeout(int sendMsgTimeout) {
		this.sendMsgTimeout = sendMsgTimeout;
	}
}
