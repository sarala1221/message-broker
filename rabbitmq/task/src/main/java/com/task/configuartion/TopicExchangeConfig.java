package com.task.configuartion;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicExchangeConfig {

	// topic
	@Value("${spring.rabbitmq.topic.pending.queue}")
	private String pendingQueue;
	@Value("${spring.rabbitmq.topic.completed.queue}")
	private String completedQueue;
	
	@Value("${spring.rabbitmq.topic.inprogress.queue}")
	private String ipQueue;
	@Value("${spring.rabbitmq.topic.exchange}")
	private String topicExchange;
	@Value("${spring.rabbitmq.topic.routingkey.pending}")
	private String pRoutingKey;
	@Value("${spring.rabbitmq.topic.routingkey.completed}")
	private String cRoutingKey;
	@Value("${spring.rabbitmq.topic.routingkey.inprogress}")
	private String ipRoutingKey;

	@Bean
	Queue pendingQueue() {
		return new Queue(pendingQueue, true);
	}
	
	@Bean
	Queue cQueue() {
		return new Queue(completedQueue, true);
	}
	
	@Bean
	Queue ipQueue() {
		return new Queue(ipQueue, true);
	}

	@Bean
	Exchange myTopicExchange() {
		return ExchangeBuilder.topicExchange(topicExchange).durable(true).build();
	}

	@Bean
	Binding pQBinding() {
		return BindingBuilder.bind(pendingQueue()).to(myTopicExchange()).with(pRoutingKey).noargs();
	}
	
	@Bean
	Binding ipQBinding() {
		return BindingBuilder.bind(ipQueue()).to(myTopicExchange()).with(ipRoutingKey).noargs();
	}
	
	@Bean
	Binding cQBinding() {
		return BindingBuilder.bind(cQueue()).to(myTopicExchange()).with(cRoutingKey).noargs();
	}

}
