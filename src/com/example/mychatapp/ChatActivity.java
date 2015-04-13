package com.example.mychatapp;

import java.io.StringReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.codebutler.android_websockets.WebSocketClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import javax.json.*;

public class ChatActivity extends Activity {
	//My WebSocket hosted on OpenShift.com 
	static final String WEBSOCKETURL = "ws://mychatws-swar.rhcloud.com:8000/chat";
	//Declaration of variables
	String name;
	EditText inputMsg;
	Button btnSend, btnLeave;
	ListView listViewMsg;
	WebSocketClient androidClient;
	SharedPreferences sp;
	TextView tvOnline;
	List<ReceiveMessage> msgList;
	ReceiveMessageListAdapter adapter;
	//onCreate Method
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Hiding title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_chat);
		//Person name from main activity
		Intent intent = getIntent();
		name = intent.getStringExtra("Name");
		//Log.d("Name", name);
		
		//Initialization of variables
		inputMsg = (EditText)findViewById(R.id.msgInput);
		btnSend = (Button)findViewById(R.id.sendBtn);
		listViewMsg = (ListView)findViewById(R.id.msgListView);
		tvOnline = (TextView)findViewById(R.id.onlineTv);
		msgList = new ArrayList<ReceiveMessage>();
		adapter = new ReceiveMessageListAdapter(this, msgList);
		
		//Setting adapter for listview
		listViewMsg.setAdapter(adapter);
		
		//Getting shared preferences
		sp = this.getSharedPreferences("com.example.myChatApp", Context.MODE_PRIVATE);
		
		//Creating WebScoket client with hosted WS URL
		androidClient = new WebSocketClient(URI.create(WEBSOCKETURL + URLEncoder.encode("?name="+name)), new WebSocketClient.Listener() {
			
			//Called when message is received
			@Override
			public void onMessage(byte[] arg0) {
				// TODO Auto-generated method stub
				Log.d("Calling","onMessage byte");
			}
			
			//Called when message is received
			@Override
			public void onMessage(String msg) {
				// TODO Auto-generated method stub
				//Log.d("Calling","onMessage string");
				//Log.d("Msg", msg);
				//Calling function to check message content
				checkmsg(msg);
			}
			
			//Called when error 
			@Override
			public void onError(Exception arg0) {
				// TODO Auto-generated method stub
				Log.d("Calling","onError");
				Log.e(ChatActivity.class.getSimpleName(), "Error! : " + arg0);
			}
			
			//Called when client disconnects
			@Override
			public void onDisconnect(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.d("Calling","onDisconnect");
			}
			
			//Called when client connects
			@Override
			public void onConnect() {
				// TODO Auto-generated method stub
				Log.d("Calling","onConnect");
			}
		}, null);
		
		//Connecting to server
		androidClient.connect();
		
		//Send Button Click Event
		btnSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Object of SendMessage
				SendMessage sm = new SendMessage("message", getSession(), inputMsg.getText().toString());
				//Sending message to server
				androidClient.send(sm.toJSON());
				//Clearing text of textbox
				inputMsg.setText("");
			}
		});

	}
	
	//On back pressed closing the socket connection
	@Override
	public void onBackPressed() {
	    //Log.d("BACK", "BackPressed");
		//Showing alert
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
	
	//Checkmsg function called when message received
	private void checkmsg(String msg) {
		//Reading text json and adding in JsonObject
		JsonReader jsonReader = Json.createReader(new StringReader(msg));
		JsonObject json = jsonReader.readObject();
		jsonReader.close();
		
		//Getting value of flag from JsonObject
		String flag = json.getString("flag");
		//if it's self then store sessionId in shared prefs.
		if(flag.equals("self")){
			//Storing in shared prefs
			sp.edit().putString("session", json.getString("sessionId")).commit();
		}
		
		//If someone joins then update count and show toast
		else if(flag.equals("new")){
			changeOnline(json.getInt("onlineCount"));
			//Checking if the user is not the sender
			if(!json.getString("sessionId").equals(getSession())){
				showToast(json.getString("name")+" has joined the conversation.");
			}
		}
		
		//If someone leaves then update count and show toast
		else if(flag.equals("exit")){
			changeOnline(json.getInt("onlineCount"));
			//Checking if the user is not the sender
			if(!json.getString("sessionId").equals(getSession())){
				showToast(json.getString("name")+" has left the conversation.");
			}
		}
		
		//If new msg then add in list and 
		else if(flag.equals("message")){
			String from = json.getString("name");
			String message = json.getString("message");
			boolean self = true;
			//Checking if the user is not the sender
			if(!json.getString("sessionId").equals(getSession())){
				self = false;
			}
			//Object of receive message
			ReceiveMessage rm = new ReceiveMessage(from, message, self);
			//Calling to add message in list
			addMessage(rm);			
		}
	}
	
	//add message in list view and notify that data has changed
	private void addMessage(final ReceiveMessage rm) {
		// Have to use runOnUiThread because nonUiThread is not allowed to change the layout
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				msgList.add(rm);
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	//Getting stored session from shared prefs
	private String getSession(){
		return sp.getString("session", null);
	}
	
	//Setting online count
	private void changeOnline(final int x){
		// Have to use runOnUiThread because nonUiThread is not allowed to change the layout
		runOnUiThread(new Runnable() {
		    public void run(){   
		    	tvOnline.setText("Online: "+x);
		    }
		});
	}
	
	//Displaying toast on screen
	private void showToast(final String msg){
		// Have to use runOnUiThread because nonUiThread is not allowed to change the layout
		runOnUiThread(new Runnable() {
		    public void run(){   
		    	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
		    }
		});
	}
}
