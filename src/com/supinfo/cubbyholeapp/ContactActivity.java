package com.supinfo.cubbyholeapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.supinfo.Adapter.ContactAdapter;
import com.supinfo.Adapter.ContactAdapter.ContactAdapterListener;
import com.supinfo.entity.Contact;

public class ContactActivity extends Activity implements ContactAdapterListener {
	public static final String PREFS_NAME = "LoginPrefs";
	EditText emailContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		
		new HttpAsyncTaskContact().execute("http://"+ settings.getString("ipServeur", "") +"/Cubbyhole/rest/Contact/id-"+settings.getInt("idUser", -1));
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

  //Traitement des données reçus
  	public class HttpAsyncTaskContact extends AsyncTask<String, Void, String> {
         @Override
         protected String doInBackground(String... urls) {

             return GET(urls[0]);
         }
         // onPostExecute displays the results of the AsyncTask.
         @Override
         protected void onPostExecute(String result) {           
             Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
             
             try{
            	 // Met les entités stockageClient dans une liste pour afficher une ListView
                ArrayList<Contact> listContact = new ArrayList<Contact>();
                 Contact[] stockageContact = gson.fromJson(result, Contact[].class);
                 
                  for (int i = 0; i < stockageContact.length; i++) {
              	   listContact.add(stockageContact[i]);
                 }
                 
                 ArrayList<Contact> listSc = listContact;
                 ContactAdapter adapter = new ContactAdapter(ContactActivity.this, listSc);
	     	       adapter.addListener(ContactActivity.this);
	     	       ListView list = (ListView) findViewById(R.id.listeContact);
	     	       list.setAdapter(adapter);
             } catch(Exception e){
          	   Toast.makeText(getBaseContext(), "Veuillez modifier l'adresse IP du serveur dans la partie 'option'", Toast.LENGTH_LONG).show();
             }
     	       
        }
     }

	@Override
	public void onClickNom(Contact item, int position) {
		// TODO Auto-generated method stub
		Toast.makeText(getBaseContext(), "GOOD", Toast.LENGTH_LONG).show();
	}
	
	public void Ajouter (View view){
		EditText contactMail = (EditText) findViewById(R.id.EditContactMail);
		String mail = contactMail.getText().toString();
		
		if(mail.equals("")){
			Toast.makeText(getBaseContext(), "Veuillez entrer une adresse mail !", Toast.LENGTH_LONG).show();  
		} else {
			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			new HttpAsyncTaskContactAjout().execute("http://"+ settings.getString("ipServeur", "") +"/Cubbyhole/rest/Contact/id-"+settings.getInt("idUser", -1)+"-"+mail);
		}
		
	}
	
	//Traitement des données reçus
  	public class HttpAsyncTaskContactAjout extends AsyncTask<String, Void, String> {
         @Override
         protected String doInBackground(String... urls) {

             return GET(urls[0]);
         }
         // onPostExecute displays the results of the AsyncTask.
         @Override
         protected void onPostExecute(String result) {           
             
      	   Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
      	   Intent accueil = new Intent(ContactActivity.this, ContactActivity.class);
      	   startActivity(accueil);
        }
     }

}
