package com.example.mychatapp;

public class ReceiveMessage {
	private String from;
	private String message;
	private Boolean self;
	public ReceiveMessage(String from, String message, Boolean self) {
		super();
		this.setFrom(from);
		this.setMessage(message);
		this.setSelf(self);
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

	public Boolean getSelf() {
		return self;
	}

	public void setSelf(Boolean self) {
		this.self = self;
	}
	
}
