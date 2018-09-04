package com.qiyuely.remex.rocketmq.producer.config;

import org.apache.rocketmq.common.message.Message;

import com.alibaba.fastjson.JSONObject;

/**
 * mq生产者默认发送器
 * 
 * @author Qiaoxin.Hong
 *
 */
public abstract class BaseMqProducerDefaultSender extends BaseMqProducerSender {
	
	/** mq主题 */
	protected String topic;
	
	/** mq tag */
	protected String tag;
	
	/**
	 * 发送
	 * @param obj
	 */
	public void send(Object obj) {
		String body = null;
		if (obj != null) {
			body = JSONObject.toJSONString(obj);
		}
		send(body);
	}
	
	/**
	 * 发送
	 * @param body
	 */
	public void send(String body) {
		Message message = new Message(topic, tag, body.getBytes());
		sendMessage(message);
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
}
