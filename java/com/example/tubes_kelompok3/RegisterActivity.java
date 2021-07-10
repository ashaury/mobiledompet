package com.example.tubes_kelompok3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {
    EditText vUsername, vPassword, vNama, vAlamat;
    ProgressDialog progressDialog;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        vUsername = (EditText)findViewById(R.id.et_username);
        vPassword = (EditText)findViewById(R.id.et_password);
        vNama = (EditText)findViewById(R.id.et_nama);
        vAlamat = (EditText)findViewById(R.id.et_alamat);
    }
    public void Register(View arg0){
        final String username = vUsername.getText().toString();
        final String password = vPassword.getText().toString();
        final String nama = vNama.getText().toString();
        final String alamat = vAlamat.getText().toString();

        String url = "http://10.0.2.2/android/tubes_kelompok3/insertUser.php?username="+username+"&password="+password+"&nama="+nama+"&alamat="+alamat;

        requestQueue = Volley.newRequestQueue(RegisterActivity.this);

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
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    Toast.makeText(RegisterActivity.this, "Data Berhasil di Tambah!.", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}