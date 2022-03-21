
package com.task.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 440220612668870100L;
	private String taskId;
	private String taskName;
//	private String rKey;
//	private String hKey;

	public Task() {
		super();
	}

	public Task(String taskId, String taskName) {
		this.taskId = taskId;
		this.taskName = taskName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

//	public String getrKey() {
//		return rKey;
//	}
//
//	public void setrKey(String rKey) {
//		this.rKey = rKey;
//	}
//
//	public String gethKey() {
//		return hKey;
//	}
//
//	public void sethKey(String hKey) {
//		this.hKey = hKey;
//	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskName=" + taskName + "]";
	}

}
