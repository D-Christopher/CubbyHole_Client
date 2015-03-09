package com.supinfo.cubbyholeapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.supinfo.Adapter.StockageClientAdapter;
import com.supinfo.Adapter.StockageClientAdapter.StockageclientAdapterListener;
import com.supinfo.Adapter.StockageClientAdapterPartage;
import com.supinfo.Adapter.StockageClientAdapterPartage.StockageclientAdapterListenerPartager;
import com.supinfo.entity.Client;
import com.supinfo.entity.Stockageclient;

public class MainActivity extends Activity implements StockageclientAdapterListener, StockageclientAdapterListenerPartager {
	public static final String PREFS_NAME = "LoginPrefs";
	
	Boolean isInternetPresent = true;
	ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Verification de la connection internet
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        
        //S'il y a une connexion internet
        if (isInternetPresent) { 
        	// Si l'utilisateur est déjà identifier
    		if (settings.getString("logged", "").toString().equals("logged")) {
    			//Récuperation des données via l'API 
    			new HttpAsyncTaskClient().execute("http://"+ settings.getString("ipServeur", "") +"/Cubbyhole/rest/client/id-"+settings.getInt("idUser", -1));
    			new HttpAsyncTaskStockageClient().execute("http://"+ settings.getString("ipServeur", "") +"/Cubbyhole/rest/StockageClient/ClientAuteur-"+settings.getInt("idUser", -1));
    			new HttpAsyncTaskStockageClientPartage().execute("http://"+ settings.getString("ipServeur", "") +"/Cubbyhole/rest/StockageClient/ClientNotAuteur-"+settings.getInt("idUser", -1));
    		} else {
    			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    			startActivity(intent);
    			
    		}
        } else {
        	Toast.makeText(getBaseContext(), "Veuillez vous connecter à internet !", Toast.LENGTH_LONG).show();
        }

    }

    // Menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Traitement des boutons du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	
    	switch (item.getItemId()) {
    	case R.id.menuContact:
    		Intent Contact = new Intent(this, ContactActivity.class);
	        startActivity(Contact);
	      break;
	    case R.id.menuProfil:
    		Intent Profil = new Intent(this, EditProfilActivity.class);
	        startActivity(Profil);
	      break;
	    case R.id.menuRrefresh:
	    	Intent main = new Intent(this, MainActivity.class);
	        startActivity(main);
	      break;
	    case R.id.logout:
	    	if (settings.getString("logged", "").toString().equals("logged")) {
	    		SharedPreferences.Editor editor = settings.edit();
				editor.remove("logged");
				editor.commit();
				finish();
				Intent retourMain = new Intent(this, MainActivity.class);
		        startActivity(retourMain);
			} else {
				Toast.makeText(getBaseContext(), "Ce deconnecté avant d'être connecté c'est compliquer !", Toast.LENGTH_LONG).show();
			}				
	      break;
	    case R.id.menuIp:
	    	Intent serveur = new Intent(this, OptionServeurActivity.class);
	        startActivity(serveur);			
	      break;
	      
	    default:
	      break;
    }
    	
        return true;
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
  	public class HttpAsyncTaskClient extends AsyncTask<String, Void, String> {
         @Override
         protected String doInBackground(String... urls) {

             return GET(urls[0]);
         }
         // onPostExecute displays the results of the AsyncTask.
         @Override
         protected void onPostExecute(String result) {           
             Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
             
             try{
                 // Met les entités projets dans une liste pour afficher une ListView
                 /*Plan plan = new Plan();
                 plan = gson.fromJson(result, Plan.class);*/
            	 
            	 Client client = new Client();
            	 client = gson.fromJson(result, Client.class);
                 
                 TextView titreClient = (TextView) findViewById(R.id.TitreClient);
                 TextView titreDetail = (TextView) findViewById(R.id.TitreDerail);
                 
                 titreClient.setText("Bienvenue "+client.getPrenom() + " " + client.getNom());
                 titreDetail.setText("Quotas quotidient : " +  client.getQuotas() + " Mo - Espace utilisé : "+ client.getEspace() + " Mo");
                 
                 /*for (int i = 0; i < client.length; i++) {
              	   listClient.add(client[i]);
                 }
                 ArrayList<Client> listP = listClient;
                 ProjetAdapter adapter = new ProjetAdapter(MainActivity.this, listP);
         	       adapter.addListener(MainActivity.this);
         	       ListView list = (ListView) findViewById(R.id.listeProjet);
         	       list.setAdapter(adapter);*/
             } catch(Exception e){
          	   //Toast.makeText(getBaseContext(), "Veuillez modifier le nom du serveur dans la partie 'option'", Toast.LENGTH_LONG).show();
             }
             
     	       
     	    //Toast.makeText(getBaseContext(), "Récupération des projets terminer", Toast.LENGTH_SHORT).show();
     	       
        }
     }
  	
  //Traitement des données reçus (StockageClient)
  	public class HttpAsyncTaskStockageClient extends AsyncTask<String, Void, String> {
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
                 ArrayList<Stockageclient> liststockageClient = new ArrayList<Stockageclient>();
                 Stockageclient[] stockageClient = gson.fromJson(result, Stockageclient[].class);
                 
                 for (int i = 0; i < stockageClient.length; i++) {
              	   liststockageClient.add(stockageClient[i]);
                 }
            	 
                 ArrayList<Stockageclient> listSc = liststockageClient;
                 StockageClientAdapter adapter = new StockageClientAdapter(MainActivity.this, listSc);
	     	       adapter.addListener(MainActivity.this);
	     	       ListView list = (ListView) findViewById(R.id.listeStockageClient);
	     	       list.setAdapter(adapter);
	     	       
             } catch(Exception e){
          	   Toast.makeText(getBaseContext(), "Veuillez modifier l'adresse IP du serveur dans la partie 'option'", Toast.LENGTH_LONG).show();
             }
             
     	       
     	    //Toast.makeText(getBaseContext(), "Récupération des projets terminer", Toast.LENGTH_SHORT).show();
     	       
        }
     }
  	
  //Traitement des données reçus (StockageClient)
  	public class HttpAsyncTaskStockageClientPartage extends AsyncTask<String, Void, String> {
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
                 ArrayList<Stockageclient> liststockageClient = new ArrayList<Stockageclient>();
                 Stockageclient[] stockageClient = gson.fromJson(result, Stockageclient[].class);
                 
                 for (int i = 0; i < stockageClient.length; i++) {
              	   liststockageClient.add(stockageClient[i]);
                 }
            	 
                 ArrayList<Stockageclient> listSc = liststockageClient;
                 StockageClientAdapterPartage adapter = new StockageClientAdapterPartage(MainActivity.this, listSc);
	     	       adapter.addListener(MainActivity.this);
	     	       ListView list = (ListView) findViewById(R.id.listeStockageClientPartager);
	     	       list.setAdapter(adapter);
	     	       
             } catch(Exception e){
          	   Toast.makeText(getBaseContext(), "Veuillez modifier l'adresse IP du serveur dans la partie 'option'", Toast.LENGTH_LONG).show();
             }
             
     	       
     	    //Toast.makeText(getBaseContext(), "Récupération des projets terminer", Toast.LENGTH_SHORT).show();
     	       
        }
     }

	@Override
	public void onClickNom(Stockageclient item, int position) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, FichierDetailActivity.class);
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String date = df.format(item.getStockageID().getDate());
		
		intent.putExtra("fichierDetailNom", item.getStockageID().getNom());
		intent.putExtra("fichierDetailDate", date);
		intent.putExtra("fichierDetailIdStockage", item.getStockageID().getStockageID().toString());
		
        startActivity(intent);
	}

	@Override
	public void onClickNomPartage(Stockageclient item, int position) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, FichierDetailPartageActivity.class);
		
		intent.putExtra("fichierDetailNom", item.getStockageID().getNom());
		intent.putExtra("fichierDetailPrenomProprio", item.getClientID().getPrenom());
		intent.putExtra("fichierDetailNomProprio", item.getClientID().getNom());
		intent.putExtra("fichierDetailIdStockage", item.getStockageID().getStockageID().toString());
		
        startActivity(intent);
	}
	
	
}
