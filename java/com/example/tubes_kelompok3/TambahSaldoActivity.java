package com.example.tubes_kelompok3;

import androidx.appcompat.app.AppCompatActivity;

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

public class TambahSaldoActivity extends AppCompatActivity {
    EditText vUsername, vSaldo;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_saldo);

        vUsername = (EditText)findViewById(R.id.et_username);
        vSaldo = (EditText)findViewById(R.id.et_saldo);
    }
    public void TambahSaldo(View arg0){
        final String username = vUsername.getText().toString();
        final String saldo = vSaldo.getText().toString();

        String url = "http://10.0.2.2/android/tubes_kelompok3/addSaldo.php?username="+username+"&saldo="+saldo;

        requestQueue = Volley.newRequestQueue(TambahSaldoActivity.this);

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
                    Intent intent = new Intent(TambahSaldoActivity.this,AdminActivity.class);
                    Toast.makeText(TambahSaldoActivity.this, "Saldo berhasil ditambah!.", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TambahSaldoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}