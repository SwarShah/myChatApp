package com.example.mychatapp;

import javax.json.Json;

public class SendMessage {
	private String flag;
	private String sessionId;
	private String message;
	
	public SendMessage(String flag, String sessionId, String message) {
		super();
		this.flag = flag;
		this.sessionId = sessionId;
		this.message = message;
	}
	
	public String toJSON(){
		return Json.createObjectBuilder()
				.add("flag", flag)
				.add("sessionId", sessionId)
				.add("message", message)
				.build().toString();
	}	
}
