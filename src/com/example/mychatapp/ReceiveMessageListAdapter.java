package com.example.mychatapp;


import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//Adapter for listview which sets the data in list view and make decision where to dispaly the message
public class ReceiveMessageListAdapter extends BaseAdapter {
	
	Context context;
	List<ReceiveMessage> messagesItems;
	
	//Constructor
	public ReceiveMessageListAdapter(Context context, List<ReceiveMessage> navDrawerItems) {
		this.context = context;
		this.messagesItems = navDrawerItems;
	}

	//Default overrided method  
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messagesItems.size();
	}
	
	//Default overrided method
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return messagesItems.get(position);
	}

	//Default overrided method
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	//Default overrided method
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Getting position of message
		ReceiveMessage rm = messagesItems.get(position);
		
		//Inflatting the layout
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
		//If user is the sender of msg then display it in right side
		if (messagesItems.get(position).getSelf()) {
			convertView = mInflater.inflate(R.layout.right,null);
		} 
		//If user is not the sender of msg then display it in left side
		else {
			convertView = mInflater.inflate(R.layout.left,null);
		}
		
		//Initalization
		TextView fromLbl = (TextView) convertView.findViewById(R.id.lblMsgFrom);
		TextView msgLbl = (TextView) convertView.findViewById(R.id.txtMsg);
		
		//Setting values
		msgLbl.setText(rm.getMessage());
		fromLbl.setText(rm.getFrom());
		return convertView;
	}

}
