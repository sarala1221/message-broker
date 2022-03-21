package com.task.publisher;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.task.model.Task;

@Service
public class TaskPublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	public TaskPublisher(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Value("${spring.rabbitmq.exchange}")
	private String exchange;

	@Value("${spring.rabbitmq.fanout.exchange}")
	private String fanoutExchange;

	@Value("${spring.rabbitmq.topic.exchange}")
	private String topicExchange;

	@Value("${spring.rabbitmq.headers.exchange}")
	private String headersExchange;

	public void send(Task task, Map<String, Object> headers) {
		String rKey = String.valueOf(headers.get("routeType"));
		rabbitTemplate.convertAndSend(exchange, rKey, task);
	}

	public void sendToTopic(Task task, String routeKey) {

		rabbitTemplate.convertAndSend(topicExchange, routeKey, task);
	}

	public void sendToFanout(Task task, String routeKey) {
		rabbitTemplate.convertAndSend(fanoutExchange, routeKey, task);
	}

	public void sendToHeaders(Task task, String taskType) {

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setHeader("task", taskType);
		JSONObject jsonObject = new JSONObject(task);
		String msgStr = jsonObject.toString();

		MessageConverter messageConverter = new SimpleMessageConverter();
		Message msg = messageConverter.toMessage(msgStr, messageProperties);
		rabbitTemplate.convertAndSend(headersExchange, "", msg);

	}
}
