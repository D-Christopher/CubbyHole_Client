package com.supinfo.cubbyholeapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class FichierDetailPartageActivity extends Activity {
	public static final String PREFS_NAME = "LoginPrefs";
    
    //initialize root directory
    File rootDir = Environment.getExternalStorageDirectory();
     
    //defining file name and url
    public String fileName = "AppOverlay.dll";
    public String fileURL = "\\\\win-dsnq5dd7d85\\CubbyHole" + File.separator + 1/*Integer.parseInt(idClient)*/ + File.separator + "AppOverlay.dll"/* nomFichier*/;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fichier_detail_partage);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	    Intent intent = getIntent();
	    
	    TextView proprio = (TextView) findViewById(R.id.fichierDetailProprio);
	    TextView nom = (TextView) findViewById(R.id.fichierDetailNom);
	    
	    proprio.setText("Appartient à " + intent.getStringExtra("fichierDetailPrenomProprio") + " " + intent.getStringExtra("fichierDetailNomProprio"));
	    nom.setText(intent.getStringExtra("fichierDetailNom"));
	}
	
	public void Telecharger(View view) {
		checkAndCreateDirectory("/Download");
		//Url url = new URL("file:\\\\win-dsnq5dd7d85\\CubbyHole" + File.separator + 1/*Integer.parseInt(idClient)*/ + File.separator + "AppOverlay.dll"/* nomFichier*/);
		
		String filename = "myfile";
		String string = "Hello world!";
		FileOutputStream outputStream;

		try {
		  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
		  outputStream.write(string.getBytes());
		  outputStream.close();
		} catch (Exception e) {
		  e.printStackTrace();
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

}
