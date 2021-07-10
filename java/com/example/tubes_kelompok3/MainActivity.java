package com.example.tubes_kelompok3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView txtuser, vSaldo;
    private String username;
    private String KEY_NAME = "username";

    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    ArrayList<HashMap<String, String>> list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_edit = (Button) findViewById(R.id.btn_edit);
        txtuser = (TextView) findViewById(R.id.txt_vusername);

        Intent i = getIntent();
        username = i.getStringExtra("KEY_NAME");
        txtuser.setText("Selamat Datang , " + username + " !");

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent inte = new Intent(MainActivity.this, EditActivity.class);
                inte.putExtra("KEY_NAME", username);
                startActivity(inte);
            }
        });

        String url = "http://10.0.2.2/android/tubes_kelompok3/getdata.php?username="+username;

        vSaldo = (TextView)findViewById(R.id.txt_vsaldo);

        requestQueue = Volley.newRequestQueue(MainActivity.this);

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
                        map.put("saldo", json.getString("saldo"));
                        list_data.add(map);
                    }
                    vSaldo.setText("Saldo anda Rp. " + list_data.get(0).get("saldo") + ",00.");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
    public void Logout(View arg0){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.putStringArrayListExtra("KEY_NAME", null);
        startActivity(intent);
    }
}