package com.example.mychatapp;

import java.io.StringReader;
import java.net.URI;
import java.net.URLEncoder;

import com.codebutler.android_websockets.WebSocketClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import javax.json.*;

public class ChatActivity extends Activity {
	//Declaration of variables
	static final String WEBSOCKETURL = "ws://mychatws-swar.rhcloud.com:8000/chat?name=";
	String name;
	EditText inputMsg;
	Button btnSend, btnLeave;
	ListView listViewMsg;
	WebSocketClient androidClient;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Hiding title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat);
		//Person name from main activity
		Intent intent = getIntent();
		name = intent.getStringExtra("Name");
		Log.d("Name", name);
		//Initialization of variables
		inputMsg = (EditText)findViewById(R.id.msgInput);
		btnSend = (Button)findViewById(R.id.sendBtn);
		listViewMsg = (ListView)findViewById(R.id.msgListView);
		btnLeave = (Button)findViewById(R.id.leaveBtn);
		sp = this.getSharedPreferences("com.example.myChatApp", Context.MODE_PRIVATE);
		androidClient = new WebSocketClient(URI.create(WEBSOCKETURL + URLEncoder.encode(name)), new WebSocketClient.Listener() {
			
			@Override
			public void onMessage(byte[] arg0) {
				// TODO Auto-generated method stub
				Log.d("Calling","onMessage byte");
			}
			
			@Override
			public void onMessage(String msg) {
				// TODO Auto-generated method stub
				Log.d("Calling","onMessage string");
				Log.d("Msg", msg);
				checkmsg(msg);
			}
			
			@Override
			public void onError(Exception arg0) {
				// TODO Auto-generated method stub
				Log.d("Calling","onError");
				Log.e(ChatActivity.class.getSimpleName(), "Error! : " + arg0);
			}
			
			@Override
			public void onDisconnect(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.d("Calling","onDisconnect");
			}
			
			@Override
			public void onConnect() {
				// TODO Auto-generated method stub
				Log.d("Calling","onConnect");
			}
		}, null);
		androidClient.connect();
		
		//Send Button Click Event
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SendMessage sm = new SendMessage("message", getSession(), inputMsg.getText().toString());
				androidClient.send(sm.toJSON());
				inputMsg.setText("");
			}
		});
		
		//Leave Button Click Event
		btnLeave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				androidClient.disconnect();
				ChatActivity.this.finish();
			}
		});
	}
	
	//On back pressed closing the socket connection
	@Override
	public void onBackPressed() {
	    //Log.d("BACK", "BackPressed");
	    new AlertDialog.Builder(this)
        .setMessage("Are you sure you want to leave chat?")
        .setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	 Log.d("SHP", getSession());
            	 androidClient.disconnect();
                 ChatActivity.this.finish();
            }
        })
        .setNegativeButton("No", null)
        .show();
	}
	
	private void checkmsg(String msg) {
		//Reading text json and adding in JsonObject
		JsonReader jsonReader = Json.createReader(new StringReader(msg));
		JsonObject json = jsonReader.readObject();
		jsonReader.close();
		String flag = json.getString("flag");
		//self to store sessionId in shared prefs.
		if(flag.equals("self")){
			sp.edit().putString("session", json.getString("sessionId")).commit();
		}
	}
	
	private String getSession(){
		return sp.getString("session", null);
	}
}
