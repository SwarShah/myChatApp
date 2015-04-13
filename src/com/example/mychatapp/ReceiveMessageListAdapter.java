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

public class ReceiveMessageListAdapter extends BaseAdapter {
	
	Context context;
	List<ReceiveMessage> messagesItems;

	public ReceiveMessageListAdapter(Context context, List<ReceiveMessage> navDrawerItems) {
		this.context = context;
		this.messagesItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messagesItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return messagesItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ReceiveMessage rm = messagesItems.get(position);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (messagesItems.get(position).getSelf()) {
			convertView = mInflater.inflate(R.layout.right,null);
		} else {
			convertView = mInflater.inflate(R.layout.left,null);
		}
		TextView fromLbl = (TextView) convertView.findViewById(R.id.lblMsgFrom);
		TextView msgLbl = (TextView) convertView.findViewById(R.id.txtMsg);
		msgLbl.setText(rm.getMessage());
		fromLbl.setText(rm.getFrom());
		return convertView;
	}

}
