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
public class FanoutExchangeConfig {
	// fanout
	@Value("${spring.rabbitmq.fanout.inprogress.queue}")
	private String fanoutIpQueue;
	@Value("${spring.rabbitmq.fanout.completed.queue}")
	private String fanoutComQueue;
	@Value("${spring.rabbitmq.fanout.pending.queue}")
	private String fanoutPenQueue;
	@Value("${spring.rabbitmq.fanout.exchange}")
	private String fanoutExchange;
	@Value("${spring.rabbitmq.fanout.routingkey.inprogress}")
	private String fanoutIPRoutingKey;
	@Value("${spring.rabbitmq.fanout.routingkey.completed}")
	private String fanoutComRoutingKey;
	@Value("${spring.rabbitmq.fanout.routingkey.pending}")
	private String fanoutPenRoutingKey;

	@Bean
	Queue fanoutIPQueue() {
		return new Queue(fanoutIpQueue, true);
	}
	@Bean
	Queue fanoutPQueue() {
		return new Queue(fanoutPenQueue, true);
	}
	@Bean
	Queue fanoutCQueue() {
		return new Queue(fanoutComQueue, true);
	}

	@Bean
	Exchange myFanoutExchange() {
		return ExchangeBuilder.fanoutExchange(fanoutExchange).durable(true).build();
	}

	@Bean
	Binding CompFanoutBinding() {
		return BindingBuilder.bind(fanoutCQueue()).to(myFanoutExchange()).with(fanoutComRoutingKey).noargs();
	}
	@Bean
	Binding penFanoutBinding() {
		return BindingBuilder.bind(fanoutPQueue()).to(myFanoutExchange()).with(fanoutPenRoutingKey).noargs();
	}
	@Bean
	Binding ipFanoutBinding() {
		return BindingBuilder.bind(fanoutIPQueue()).to(myFanoutExchange()).with(fanoutIPRoutingKey).noargs();
	}
}
