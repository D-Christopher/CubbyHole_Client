package com.supinfo.cubbyholeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class GenerationActivity extends Activity {
	public static final String PREFS_NAME = "LoginPrefs";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generation);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		Intent intent = getIntent();
		
		EditText lien = (EditText) findViewById(R.id.GenerationLien);
		lien.setText(intent.getStringExtra("fichierLien"));
	}

}
