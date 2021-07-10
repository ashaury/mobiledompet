package com.example.tubes_kelompok3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class EditActivity extends AppCompatActivity {
    TextView txtHello;
    EditText vUsername, vPassword, vNama, vAlamat;
    Button btn_edit;
    private String usernamee;
    private String KEY_NAME = "username";
    ProgressDialog progressDialog;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        txtHello = (TextView) findViewById(R.id.txt_vusername);

        Bundle extras = getIntent().getExtras();
        usernamee = extras.getString("KEY_NAME");

        String url = "http://10.0.2.2/android/tubes_kelompok3/getdata.php?username="+usernamee;

        vUsername = (EditText)findViewById(R.id.et_username);
        vPassword = (EditText)findViewById(R.id.et_password);
        vNama = (EditText)findViewById(R.id.et_namalengkap);
        vAlamat = (EditText)findViewById(R.id.et_alamat);

        requestQueue = Volley.newRequestQueue(EditActivity.this);

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("users");
                    for (int a = 0; a < jsonArray.length(); a ++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map  = new HashMap<String, String>();
                        map.put("username", json.getString("username"));
                        map.put("password", json.getString("password"));
                        map.put("nama", json.getString("nama"));
                        map.put("alamat", json.getString("alamat"));
                        list_data.add(map);
                    }
                    vUsername.setText(list_data.get(0).get("username"));
                    vPassword.setText(list_data.get(0).get("password"));
                    vNama.setText(list_data.get(0).get("nama"));
                    vAlamat.setText(list_data.get(0).get("alamat"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void updateData(View arg0){
        final String username = vUsername.getText().toString();
        final String password = vPassword.getText().toString();
        final String nama = vNama.getText().toString();
        final String alamat = vAlamat.getText().toString();

        String url = "http://10.0.2.2/android/tubes_kelompok3/updateUser.php?username="+username+"&password="+password+"&nama="+nama+"&alamat="+alamat;

        requestQueue = Volley.newRequestQueue(EditActivity.this);

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int a = 0; a < jsonArray.length(); a ++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map  = new HashMap<String, String>();
                        map.put("true", json.getString("true"));
                        map.put("false", json.getString("false"));
                        list_data.add(map);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Intent intent = new Intent(EditActivity.this,MainActivity.class);
                    intent.putExtra("KEY_NAME", username);
                    Toast.makeText(EditActivity.this, "Data Berhasil di Edit!.", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}