package com.example.mychatapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


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
        
    }

}
