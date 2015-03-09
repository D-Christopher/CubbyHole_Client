package com.supinfo.cubbyholeapp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.supinfo.entity.Contact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FichierDetailActivity extends Activity{
	public static final String PREFS_NAME = "LoginPrefs";
     
    //initialize root directory
    File rootDir = Environment.getExternalStorageDirectory();
     
    //defining file name and url
    //public String fileName = "AppOverlay.dll";
    //public String fileURL = "\\\\win-dsnq5dd7d85\\CubbyHole" + File.separator + 1/*Integer.parseInt(idClient)*/ + File.separator + "AppOverlay.dll"/* nomFichier*/;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fichier_detail);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
				
	    Intent intent = getIntent();
	    
	    TextView date = (TextView) findViewById(R.id.fichierDetailDate);
	    TextView nom = (TextView) findViewById(R.id.fichierDetailNom);
	    
	    date.setText(intent.getStringExtra("fichierDetailDate"));
	    nom.setText(intent.getStringExtra("fichierDetailNom"));
	}
	
	public void Partager (View view){
		Intent intent2 = getIntent();
		Intent intent = new Intent(FichierDetailActivity.this, FichierPartageActivity.class);
		
		intent.putExtra("idStockage", intent2.getStringExtra("fichierDetailIdStockage"));
		
        startActivity(intent);
	}
	
	public void Supprimer(View view){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		Intent intent = getIntent();
		
		int idStockage = Integer.parseInt(intent.getStringExtra("fichierDetailIdStockage"));
		String nomFichier = intent.getStringExtra("fichierDetailNom");
		
		new HttpAsyncTaskSupprFile().execute("http://"+ settings.getString("ipServeur", "") + "/Cubbyhole/rest/suprFile/id-"+settings.getInt("idUser", -1)+"-" +idStockage+"-"+nomFichier);
	}
	
	public void Generer (View view){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		Intent intent = getIntent();
		Intent intent2 = new Intent(FichierDetailActivity.this, GenerationActivity.class);
		
		int idStockage = Integer.parseInt(intent.getStringExtra("fichierDetailIdStockage"));
		String fichierLien = "http://"+ settings.getString("ipServeur", "") + "/Cubbyhole/PartagePublic?idStockage="+idStockage+"idClient="+settings.getInt("idUser", -1);
		
		intent2.putExtra("fichierLien", fichierLien);
		
        startActivity(intent2);
	}
	
	public void Telecharger(View view) throws IOException  {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		checkAndCreateDirectory("/Download");
		
		//new HttpAsyncTaskDownloadFile().execute("http://"+ settings.getString("ipServeur", "") + "/Cubbyhole/rest/download/test");
		
		try{
			String filePath = "\\\\192.168.2.100\\CubbyHole\\1\\UML3NET.jpg";
	        File file = new File(filePath);
		    OutputStream out = new FileOutputStream(rootDir + "/Pictures/test.jpg");

		    // Transfer bytes from in to out
		    byte[] buf = new byte[1024];
		    int len;

		    DataInputStream in = new DataInputStream(new FileInputStream(file));
	        
	        // reads the file's bytes and writes them to the response stream
	        while ((in != null) && ((len = in.read(buf)) != -1))
	        {
	        	out.write(buf,0,len);
	        }
		    
		    in.close();
		    out.close();
		} catch(Exception e){
			Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
		
	}
	
	private class HttpAsyncTaskDownloadFile extends AsyncTask<String, Void, String> {
	       @Override
	       protected String doInBackground(String... urls) {

	           return GET(urls[0]);
	       }
	       // onPostExecute displays the results of the AsyncTask.
	       @Override
	       protected void onPostExecute(String result) {           
	           Gson gson = new GsonBuilder().create();
	           
	           try{
	        	// Met les entités catégorie dans un tableau
	               //Contact[] contact = gson.fromJson(result, Contact[].class);
	               Toast.makeText(getBaseContext(), result.toString(), Toast.LENGTH_LONG).show();
	           } catch (Exception e){
	        	   Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
	           }
	   	       
	   		}
		}
	
	private void checkAndCreateDirectory(String dirName) {
		// TODO Auto-generated method stub
		File new_dir = new File( rootDir + dirName );
        if( !new_dir.exists() ){
            new_dir.mkdirs();
        }
	}

	protected void onPostExecute(String unused) {
		//dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
		Toast.makeText(getBaseContext(), "Téléchargement effectué avec succée !", Toast.LENGTH_LONG).show();
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

  	//Traitement des données reçus (Client)
  	public class HttpAsyncTaskSupprFile extends AsyncTask<String, Void, String> {
         @Override
         protected String doInBackground(String... urls) {

             return GET(urls[0]);
         }
         // onPostExecute displays the results of the AsyncTask.
         @Override
         protected void onPostExecute(String result) {
        	 if(result.equalsIgnoreCase("ok")){
        		Intent retourMain = new Intent(FichierDetailActivity.this, MainActivity.class);
 		        startActivity(retourMain);
        	 } else {
        		 Toast.makeText(getBaseContext(), "Erreur lors de la supression", Toast.LENGTH_LONG).show();
        	 }
        	 
        }
     }

}
