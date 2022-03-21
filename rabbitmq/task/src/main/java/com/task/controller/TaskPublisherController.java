package com.task.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.model.Task;
import com.task.publisher.TaskPublisher;

@RestController
@RequestMapping("/api/v1/task")
public class TaskPublisherController {
	private static final Logger logger = LoggerFactory.getLogger(TaskPublisherController.class);

	@Autowired
	private TaskPublisher publisher;
	@Value("${app.message}")
	private String message;

	@PostMapping
	public ResponseEntity<String> publishTask(@RequestBody Task task, @RequestHeader Map<String, Object> headers) {

		try {
			publisher.send(task, headers);

			return new ResponseEntity<String>("Message Sent Successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("Message could not Sent!!", HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping(value = "/topic")
	public ResponseEntity<String> publishTaskToTopicE(@RequestBody Task task,
			@RequestHeader("routeKey") String routeKey, @RequestHeader(required = false, name = "queue") String queue) {
		logger.info("rKey >>> {},  Queue >> {}", routeKey, queue);
		try {
			publisher.sendToTopic(task, routeKey);
			return new ResponseEntity<String>("Message Sent Successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("Message could not Sent!!", HttpStatus.EXPECTATION_FAILED);
		}

	}

	@PostMapping(value = "/fanout")
	public ResponseEntity<String> publishTasktoFanoutE(@RequestBody Task task,
			@RequestHeader("taskType") String taskType) {

		try {
			publisher.sendToFanout(task, taskType);

			return new ResponseEntity<String>("Message Sent Successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("Message could not Sent!!", HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping(value = "/headers")
	public ResponseEntity<String> publishTaskToHeaderE(@RequestBody Task task,
			@RequestHeader("taskType") String taskType) {
		logger.info("taskType >>> {}", taskType);
		try {
			publisher.sendToHeaders(task, taskType);

			return new ResponseEntity<String>("Message Sent Successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("Message could not Sent!!", HttpStatus.EXPECTATION_FAILED);
		}
	}
}
