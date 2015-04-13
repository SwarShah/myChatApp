package com.example.mychatapp;

import javax.json.Json;

//Class for contents of Message that needs to send message
public class SendMessage {
	private String flag;
	private String sessionId;
	private String message;
	
	//Constructor
	public SendMessage(String flag, String sessionId, String message) {
		super();
		this.flag = flag;
		this.sessionId = sessionId;
		this.message = message;
	}
	
	//toJSON method to convert the data in JSON object
	public String toJSON(){
		return Json.createObjectBuilder()
				.add("flag", flag)
				.add("sessionId", sessionId)
				.add("message", message)
				.build().toString();
	}	
}
