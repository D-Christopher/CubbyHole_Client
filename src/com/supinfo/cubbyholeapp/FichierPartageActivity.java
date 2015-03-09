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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.supinfo.Adapter.ChoixContactAdaper.ChoixContactAdapterListener;
import com.supinfo.entity.Contact;

public class FichierPartageActivity extends Activity implements OnItemSelectedListener, ChoixContactAdapterListener {
	public static final String PREFS_NAME = "LoginPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fichier_partage);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		
		new HttpAsyncTaskContact().execute("http://"+ settings.getString("ipServeur", "") +"/Cubbyhole/rest/Contact/id-"+settings.getInt("idUser", -1));
	}

	public void Partager (View view){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		Intent intent = getIntent();
		
		int idStockage = Integer.parseInt(intent.getStringExtra("idStockage"));
		int idClient = settings.getInt("idUser", -1);
		
		Spinner spinner = (Spinner) findViewById(R.id.choixContact);
		String valToSet = spinner.getSelectedItem().toString(); 
		
		if(valToSet.equals("Aucune selection")){
			Toast.makeText(getBaseContext(), "Veuillez selectionner un contact", Toast.LENGTH_LONG).show();
		} else {
			new HttpAsyncTaskPartageFile().execute("http://"+ settings.getString("ipServeur", "") +"/Cubbyhole/rest/partageFile/"+valToSet+"-"+idStockage+"-"+idClient);
			//Toast.makeText(getBaseContext(), valToSet, Toast.LENGTH_LONG).show();
		}
		
	}
	
	private class HttpAsyncTaskContact extends AsyncTask<String, Void, String> {
       @Override
       protected String doInBackground(String... urls) {

           return GET(urls[0]);
       }
       // onPostExecute displays the results of the AsyncTask.
       @Override
       protected void onPostExecute(String result) {           
           Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
           
           try{
        	// Met les entités catégorie dans un tableau
               Contact[] contact = gson.fromJson(result, Contact[].class);
               String[] contactTab = new String[contact.length +1];
               for (int i = 0; i < contact.length; i++) {
            	   contactTab[i] = contact[i].getClientID().getEmail();
               }
       	       
               contactTab[contact.length] = "Aucune selection";
               
               //Récupère et affiche les données dans la liste déroulante
       	       Spinner choixContact = (Spinner) findViewById(R.id.choixContact);
       	       
       	       ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(FichierPartageActivity.this, android.R.layout.simple_spinner_item, contactTab);
       	       adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		
       	    choixContact.setAdapter(adapter_state);
       	    choixContact.setOnItemSelectedListener(FichierPartageActivity.this);
       	    choixContact.setSelection(contact.length);
           } catch (Exception e){
        	   
           }
   	       
   		}
	}
	
	private class HttpAsyncTaskPartageFile extends AsyncTask<String, Void, String> {
	       @Override
	       protected String doInBackground(String... urls) {

	           return GET(urls[0]);
	       }
	       // onPostExecute displays the results of the AsyncTask.
	       @Override
	       protected void onPostExecute(String result) {           
	    	   Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
	   	       
	   		}
		}
	
	//Envoi la requete, receptionne et converti le JSON
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

  	//Converti en String
  	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
         BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
         String line = "";
         String result = "";
         while((line = bufferedReader.readLine()) != null)
             result += line;

         inputStream.close();
         return result;
     }

	@Override
	public void onClickNom(Contact item, int position) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
