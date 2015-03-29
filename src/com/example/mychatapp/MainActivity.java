package com.example.mychatapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	private Button btnConnect;
	private EditText etName;
	String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConnect = (Button)findViewById(R.id.connectBTN);
        etName = (EditText)findViewById(R.id.nameET);

        btnConnect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name = etName.getText().toString();
				if(!name.trim().isEmpty()){
					Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG);
				}
				else{
					Toast.makeText(getApplicationContext(), "Please enter your name.", Toast.LENGTH_LONG).show();
				}
			}
		});
    }

}
