package com.supinfo.cubbyholeapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class OptionServeurActivity extends Activity {
	public static final String PREFS_NAME = "LoginPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_serveur);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        
        if(settings.contains("ipServeur")){
        	TextView ipServeur = (TextView) findViewById(R.id.serveurIp);
        	ipServeur.setText(settings.getString("ipServeur", ""));
        }
	}
	
	public void validerIp(View view){
		EditText editIp = (EditText) findViewById(R.id.serveurEdit);
		
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();		
		editor.putString("ipServeur", editIp.getText().toString());
		editor.commit();
		
		Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
	}

}
