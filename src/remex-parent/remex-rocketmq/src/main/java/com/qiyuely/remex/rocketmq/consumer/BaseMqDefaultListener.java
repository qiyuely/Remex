package com.qiyuely.remex.rocketmq.consumer;

import org.apache.rocketmq.common.message.MessageExt;

import com.qiyuely.remex.utils.StringUtils;

/**
 * mq监听默认基础类
 * 
 * @author Qiaoxin.Hong
 *
 */
public abstract class BaseMqDefaultListener extends BaseMqListener {
	
	/**
	 * 消息处理
	 * @param body
	 * @return
	 */
	protected abstract boolean handleMessageBody(String body);

	/**
	 * 消息消费
	 */
	@Override
	protected boolean handleMessage(MessageExt messageExt) {
		String body = new String(messageExt.getBody());
		
		if (StringUtils.isBlank(body)) {
			return true;
		}
		
		//消息处理
		boolean result = handleMessageBody(body);
		
		return result;
	}
}
