package com.supinfo.cubbyholeapp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
 





import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
 
public class RegisterActivity extends Activity {
 
	public static final String PREFS_NAME = "LoginPrefs";
 
    private static final String TAG = "RegisterActivity";
     
    /** Appelé lors de la création de l'activité */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

     
    public void sendInscription(View vw) {
    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String serviceUrl = "http://"+settings.getString("ipServeur", "")+"/Cubbyhole/rest/client/newClient";
    	
        EditText edNom = (EditText) findViewById(R.id.editNom);
        EditText edPrenom = (EditText) findViewById(R.id.editPrenom);
        EditText edPassword1 = (EditText) findViewById(R.id.editPassword1);
        EditText edPassword2 = (EditText) findViewById(R.id.editPassword2);
        EditText edEmail = (EditText) findViewById(R.id.editMail);
 
        String nom = edNom.getText().toString();
        String prenom = edPrenom.getText().toString();
        String password1 = edPassword1.getText().toString();
        String password2 = edPassword2.getText().toString();
        String email = edEmail.getText().toString();
 
        if (nom.equals("") || prenom.equals("") || password1.equals("") || password2.equals("") || password2.equals("") || email.equals("")) {
            Toast.makeText(this, "Merci de remplir tous les champs.",
                    Toast.LENGTH_LONG).show();
            return;
        }
        
        WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this, "Enregistrement...");
 
        wst.addNameValuePair("nom", nom);
        wst.addNameValuePair("prenom", prenom);
        wst.addNameValuePair("password", password1);
        wst.addNameValuePair("email", email);
 
        // POST
        wst.execute(new String[] { serviceUrl });
 
    }
 
    public void handleResponse(String response) {
         
    	 EditText edNom = (EditText) findViewById(R.id.editNom);
         EditText edPrenom = (EditText) findViewById(R.id.editPrenom);
         EditText edPassword1 = (EditText) findViewById(R.id.editPassword1);
         EditText edPassword2 = (EditText) findViewById(R.id.editPassword2);
         EditText edEmail = (EditText) findViewById(R.id.editMail);
         
         edNom.setText("");
         edPrenom.setText("");
         edPassword1.setText("");
         edPassword2.setText("");
         edEmail.setText("");
         
        try {
             	
            JSONObject jso = new JSONObject(response);
             
        	
        	
            String nom = jso.getString("nom");
            String prenom = jso.getString("prenom");
            String password = jso.getString("password");
            String email = jso.getString("email");
             
            edNom.setText(nom);
            edPrenom.setText(prenom);
            edPassword1.setText(password);
            edPassword2.setText(password);
            edEmail.setText(email);         
             
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
         
    }
 
    private void hideKeyboard() {
 
        InputMethodManager inputManager = (InputMethodManager) RegisterActivity.this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
 
        inputManager.hideSoftInputFromWindow(
                RegisterActivity.this.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
     
     
    private class WebServiceTask extends AsyncTask<String, Integer, String> {
 
        public static final int POST_TASK = 1;
        public static final int GET_TASK = 2;
         
        private static final String TAG = "WebServiceTask";
 
        // connection timeout, in milliseconds (waiting to connect)
        private static final int CONN_TIMEOUT = 3000;
         
        // socket timeout, in milliseconds (waiting for data)
        private static final int SOCKET_TIMEOUT = 5000;
         
        private int taskType = GET_TASK;
        private Context mContext = null;
        private String processMessage = "Enregistrement...";
 
        private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
 
        private ProgressDialog pDlg = null;
 
        public WebServiceTask(int taskType, Context mContext, String processMessage) {
 
            this.taskType = taskType;
            this.mContext = mContext;
            this.processMessage = processMessage;
        }
 
        public void addNameValuePair(String name, String value) {
 
            params.add(new BasicNameValuePair(name, value));
        }
 
        private void showProgressDialog() {
             
            pDlg = new ProgressDialog(mContext);
            pDlg.setMessage(processMessage);
            pDlg.setProgressDrawable(mContext.getWallpaper());
            pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDlg.setCancelable(false);
            pDlg.show();
 
        }
 
        @Override
        protected void onPreExecute() {
 
            hideKeyboard();
            showProgressDialog();
 
        }
 
        protected String doInBackground(String... urls) {
 
            String url = urls[0];
            String result = "";
 
            HttpResponse response = doResponse(url);
 /*
            if (response == null) {
                return result;
            } else {
 
                try {
 
                    result = inputStreamToString(response.getEntity().getContent());
 
                } catch (IllegalStateException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
 
                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                }
 
            }
 */
            return result;

        }
 
        @Override
        protected void onPostExecute(String response) {
             
            //handleResponse(response);
            pDlg.dismiss();
            
            Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
	        startActivity(login);
             
        }
         
        // Establish connection and socket (data retrieval) timeouts
        private HttpParams getHttpParams() {
             
            HttpParams htpp = new BasicHttpParams();
             
            HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
            HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);
             
            return htpp;
        }
         
        private HttpResponse doResponse(String url) {
             
            // Use our connection and data timeouts as parameters for our
            // DefaultHttpClient
            HttpClient httpclient = new DefaultHttpClient(getHttpParams());
 
            HttpResponse response = null;
 
            try {
                switch (taskType) {
 
                case POST_TASK:
                    HttpPost httppost = new HttpPost(url);
                    // Add parameters
                    httppost.setEntity(new UrlEncodedFormEntity(params));
 
                    response = httpclient.execute(httppost);
                    break;
                case GET_TASK:
                    HttpGet httpget = new HttpGet(url);
                    response = httpclient.execute(httpget);
                    break;
                }
            } catch (Exception e) {
 
                Log.e(TAG, e.getLocalizedMessage(), e);
 
            }
 
            return response;
        }
         
        private String inputStreamToString(InputStream is) {
 
            String line = "";
            StringBuilder total = new StringBuilder();
 
            // Wrap a BufferedReader around the InputStream
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
 
            try {
                // Read response until the end
                while ((line = rd.readLine()) != null) {
                    total.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }
 
            // Return full string
            return total.toString();
        }

	
    }
}
