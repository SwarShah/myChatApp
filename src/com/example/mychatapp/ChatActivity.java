package com.example.mychatapp;

import java.net.URI;
import java.net.URLEncoder;

import com.codebutler.android_websockets.WebSocketClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatActivity extends Activity {
	//Declaration of variables
	String name;
	EditText inputMsg;
	Button btnSend;
	ListView listViewMsg;
	WebSocketClient androidClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		//Person name from main activity
		Intent intent = getIntent();
		name = intent.getStringExtra("Name");
		
		//Initialization of variables
		inputMsg = (EditText)findViewById(R.id.msgInput);
		btnSend = (Button)findViewById(R.id.sendBtn);
		listViewMsg = (ListView)findViewById(R.id.msgListView);
		androidClient = new WebSocketClient(URI.create(R.string.websocketurl + name), new WebSocketClient.Listener() {
			
			@Override
			public void onMessage(byte[] arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMessage(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(Exception arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDisconnect(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onConnect() {
				// TODO Auto-generated method stub
				
			}
		}, null);
		androidClient.connect();
	}
}
