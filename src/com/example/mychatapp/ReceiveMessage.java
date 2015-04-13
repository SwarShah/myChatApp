package com.example.mychatapp;

public class ReceiveMessage {
	private String from;
	private String message;
	
	public ReceiveMessage(String from, String message) {
		super();
		this.setFrom(from);
		this.setMessage(message);
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
