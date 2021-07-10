package com.example.tubes_kelompok3;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public EditText txt_username, txt_password;
    public String KEY_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_username = (EditText) findViewById(R.id.txt_username);
        txt_password = (EditText) findViewById(R.id.txt_password);
    }
    public void Register(View arg0){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    // Triggers when Login Button clicked
    public void checkLogin(View arg0){
        final String username = txt_username.getText().toString();
        final String password = txt_password.getText().toString();
        new AsyncLogin().execute(username,password);
    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(LoginActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params){
            try{
                //Enter URL address where your php file resides
                url = new URL("http://10.0.2.2/android/tubes_kelompok3/login.php");
            }catch (MalformedURLException e){
                //TODO auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try{
                //Setup HttpURLConnection class to send and recieve data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");
                //setDoInput and setDoOutput method depict handling of both send and recieve
                conn.setDoInput(true);
                conn.setDoOutput(true);

                //Append parameters to URL
                Uri.Builder builder = new Uri.Builder();
                builder.appendQueryParameter("username", params[0]);
                builder.appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                //open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                conn.connect();
            }catch(IOException e){
                e.printStackTrace();
                return "exception";
            }
            try{
                int response_code = conn.getResponseCode();
                //check if successfull connection made
                if(response_code == HttpURLConnection.HTTP_OK){
                    //read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) !=null){
                        result.append(line);
                    }
                    //pass data to onPostExecute method
                    return(result.toString());
                }else{
                    return("unsuccessful");
                }
            }catch(IOException e){
                e.printStackTrace();
                return "exception";
            }finally{
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result){
            //this method will be running on UI thread
            pdLoading.dismiss();
            String username = txt_username.getText().toString();
            if(result.equalsIgnoreCase("true")){
                String admin="admin";
                if(username.equals(admin)){
                    Intent intent = new Intent(LoginActivity.this,AdminActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("KEY_NAME", username);
                    startActivity(intent);
                }
                LoginActivity.this.finish();
            }else if(result.equalsIgnoreCase("false")){
                //if username and password do not match display error message
                Toast.makeText(LoginActivity.this,"Username atau password salah",Toast.LENGTH_LONG).show();
            }else if(result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsucessful")){
                Toast.makeText(LoginActivity.this, "Koneksi bermasalah !.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(LoginActivity.this, result.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }

}
