package com.task.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.consumer.TaskReceiver;
import com.task.model.Task;

@RestController
@RequestMapping("/api/v1/task")
public class TaskConsumerController {

	static final Logger logger = LoggerFactory.getLogger(TaskConsumerController.class);

	@Value("${app.message}")
	private String message;

	@Value("${spring.rabbitmq.queue}")
	private String queue;

	@Autowired
	private TaskReceiver receiver;

	@GetMapping(value = "/topic")
	public ResponseEntity<Task> receiveTask() {

		Task task = receiver.receiveMessage();
		if(task == null) {
			return new ResponseEntity<Task>(task, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Task>(task, HttpStatus.OK);
	}
}
