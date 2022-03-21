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
public class DirectExchangeConfig {

	@Value("${spring.rabbitmq.queue}")
	private String queue;
	@Value("${spring.rabbitmq.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.routingkey}")
	private String routingKey;

	@Bean
	Queue queue() {
		return new Queue(queue, true);
	}

	@Bean
	Exchange myExchange() {
		return ExchangeBuilder.directExchange(exchange).durable(true).build();
	}

	@Bean
	Binding binding() {
		return BindingBuilder.bind(queue()).to(myExchange()).with(routingKey).noargs();
	}
}
