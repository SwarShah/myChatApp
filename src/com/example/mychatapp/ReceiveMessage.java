package com.example.mychatapp;

//Class for received message
public class ReceiveMessage {
	private String from;
	private String message;
	private Boolean self;
	
	//Constructor
	public ReceiveMessage(String from, String message, Boolean self) {
		super();
		this.setFrom(from);
		this.setMessage(message);
		this.setSelf(self);
	}
	
	//Getters and setters
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
