package com.supinfo.cubbyholeapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.supinfo.entity.Client;

public class LoginActivity extends Activity {
	public static final String PREFS_NAME = "LoginPrefs";
	static String email = "";
	
	EditText emailLogin;
	EditText passwordLogin;
	String mdp ;
	String mail ;
	String mdpDecrypt ="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		/*if (settings.getString("logged", "").toString().equals("logged")) {
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
		} else {
			emailLogin = (EditText) findViewById(R.id.emailLogin);
			passwordLogin = (EditText) findViewById(R.id.passwordLogin);
		}*/
	}
	
	public void register (View view){
		Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
	}
	
	public void signIn(View view) {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		emailLogin = (EditText) findViewById(R.id.emailLogin);
		passwordLogin = (EditText) findViewById(R.id.passwordLogin);
		mdp = passwordLogin.getText().toString();
		mail = emailLogin.getText().toString();
		new HttpAsyncTask().execute("http://"+settings.getString("ipServeur", "")+"/Cubbyhole/rest/client/"+mail+"-"+mdp);
	}
	
	
	public static String GET(String url){
	       InputStream inputStream = null;
	       String result = "";
	       try {

	           // create HttpClient
	           HttpClient httpclient = new DefaultHttpClient();

	           // make GET request to the given URL
	           HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

	           // receive response as inputStream
	           inputStream = httpResponse.getEntity().getContent();

	           // convert inputstream to string
	           if(inputStream != null)
	               result = convertInputStreamToString(inputStream);
	           else
	               result = "Did not work";

	       } catch (Exception e) {
	           Log.d("InputStream", e.getLocalizedMessage());
	       }
	       Log.d("TestNumber1", result);
	       return result;
	   }

	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	       BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	       String line = "";
	       String result = "";
	       while((line = bufferedReader.readLine()) != null)
	           result += line;

	       inputStream.close();
	       return result;
	   }

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
	       @Override
	       protected String doInBackground(String... urls) {

	           return GET(urls[0]);
	                                  }
	       // onPostExecute displays the results of the AsyncTask.
	       @Override
	       protected void onPostExecute(String result) {
	    	   Log.d("ERRRRREEEEEEEUUUUUUURRR ", result);
	    	   Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	    	   Client client = new Client();
	    	   if(result == null)
	    		   client = null;
	    	   else
	    	   client = gson.fromJson(result, Client.class);
	    	   

	          try {
	        	
	           if(client !=null ){
	        	   SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("logged", "logged");
					editor.putInt("idUser", client.getClientID());
					editor.putString("mailUser", client.getEmail());
					editor.commit();
	        	   
	        	   Intent accueil = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(accueil);
	          }
	           else
	           {
	        	   Toast.makeText(getBaseContext(), "Mot de passe ou email non valide", Toast.LENGTH_LONG).show();
	        	   emailLogin.setText("");
	        	   passwordLogin.setText("");
	           }
	           }
	          catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	   
	             
	      }
   }
}
