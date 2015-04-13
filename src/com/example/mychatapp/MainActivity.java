package com.example.mychatapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	//Declaration
	private Button btnConnect;
	private EditText etName;
	String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConnect = (Button)findViewById(R.id.connectBTN);
        etName = (EditText)findViewById(R.id.nameET);
        //On press of connect button
        btnConnect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Getting text of textbox
				name = etName.getText().toString();
				//if it's not empty
				if(!name.trim().isEmpty()){
					//Testing
					//Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
					//Switch to next screen
					Intent i = new Intent(getApplicationContext(), ChatActivity.class);
					i.putExtra("Name", name);
					startActivity(i);
				}
				else{
					//Show error message on invalid
					Toast.makeText(getApplicationContext(), "Please enter your name.", Toast.LENGTH_LONG).show();
				}
			}
		});
    }

}
