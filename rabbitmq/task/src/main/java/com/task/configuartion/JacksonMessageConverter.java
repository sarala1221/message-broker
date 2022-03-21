package com.task.configuartion;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class JacksonMessageConverter extends Jackson2JsonMessageConverter {
	public JacksonMessageConverter() {
		super();
	}

	@Override
	public Object fromMessage(Message message) {
		message.getMessageProperties().setContentType("application/json");
		return super.fromMessage(message);
	}
}
