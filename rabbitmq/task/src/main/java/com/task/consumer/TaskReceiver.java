package com.task.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.task.model.Task;

@Component
public class TaskReceiver implements RabbitListenerConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(TaskReceiver.class);

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${spring.rabbitmq.queue}")
	private String queue;

	@Autowired
	public TaskReceiver(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		// TODO Auto-generated method stub

	}

	@RabbitListener(queues = "${spring.rabbitmq.queue}")
	// Listens to automatically when a message arrived to the queue
	public void receivedMessage(Task task) {
		logger.info("Message Arrived to Direct Q and Can be processed further based on requirement...."
				+ task.getTaskName());
	}

	// @RabbitListener(queues =
	// "#{'${spring.rabbitmq.topic.inprogress.queue},${spring.rabbitmq.topic.pending.queue},${spring.rabbitmq.topic.completed.queue}'.split(',')}")
	// Listens to automatically when a message arrived to the queue
	public void receivedMessageFromTopicQ(Task task) {
		logger.info("Message Arrived to Topic Q and Can be processed further based on requirement...."
				+ task.getTaskName());
	}

	@RabbitListener(queues = "#{'${spring.rabbitmq.fanout.inprogress.queue},${spring.rabbitmq.fanout.pending.queue},${spring.rabbitmq.fanout.completed.queue}'.split(',')}")
	// Listens to automatically when a message arrived to the queue
	public void receivedMessageFromFanout(Task task) {
		logger.info("Message Arrived to Fanout Q and Can be processed further based on requirement...."
				+ task.getTaskName());
	}

	@RabbitListener(queues = "#{'${spring.rabbitmq.headers.inprogress.queue},${spring.rabbitmq.headers.pending.queue},${spring.rabbitmq.headers.completed.queue}'.split(',')}")
	// Listens to automatically when a message arrived to the queue
	public void receivedMessageFromHeaderQ(String msg) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

			Task jsonMsg = mapper.readValue(msg, Task.class);
			logger.info("Message Arrived to Header Q and Can be processed further based on requirement...."
					+ jsonMsg.getTaskName());
		} catch (IOException e) {
			logger.error("Error while conversion");
		}
	}

	public Task receiveMessage() {
		Message message = rabbitTemplate.receive(queue);
		if (null == message)
			return null;
		ObjectMapper mapper = new ObjectMapper();
		Task task = null;
		try {

			task = mapper.readValue(message.getBody(), Task.class);
		} catch (IOException e) {
			logger.error("Error while receiving Message ", e);
		}
		logger.info("Task Details Received is.. " + task);
		return task;
	}

}
