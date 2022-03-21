package com.task.configuartion;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeadersExchangeConfig {
	// headers
	@Value("${spring.rabbitmq.headers.completed.queue}")
	private String completedHQ;
	@Value("${spring.rabbitmq.headers.pending.queue}")
	private String pendingHQ;
	@Value("${spring.rabbitmq.headers.inprogress.queue}")
	private String ipHQ;
	@Value("${spring.rabbitmq.headers.exchange}")
	private String headersExchange;

	@Bean
	Queue ipHQueue() {
		return new Queue(ipHQ, true);
	}

	@Bean
	Queue cHQueue() {
		return new Queue(completedHQ, true);
	}

	@Bean
	Queue pHQueue() {
		return new Queue(pendingHQ, true);
	}

	@Bean
	HeadersExchange myheadersExchange() {
		System.out.println("headersExchange >> " + headersExchange);
		return ExchangeBuilder.headersExchange(headersExchange).durable(true).build();
	}

	@Bean
	Binding cHQueueBinding() {
		System.out.println("cHQueue >> ");
		return BindingBuilder.bind(cHQueue()).to(myheadersExchange()).where("task").matches("completed");
	}

	@Bean
	Binding penQBinding() {
		System.out.println("pHQueue >> ");
		return BindingBuilder.bind(pHQueue()).to(myheadersExchange()).where("task").matches("pending");
	}

	@Bean
	Binding iPBinding() {
		System.out.println("ipHQueue >> ");
		return BindingBuilder.bind(ipHQueue()).to(myheadersExchange()).where("task").matches("ip");
	}
}
