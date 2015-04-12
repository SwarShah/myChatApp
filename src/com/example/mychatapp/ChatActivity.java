package com.example.mychatapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ChatActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		//Person name from main activity
		Intent intent = getIntent();
		intent.getStringExtra("Name");
		
	}
}
