package com.example.tubes_kelompok3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    public String KEY_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    }
    public void TambahS(View arg0){
        Intent intent = new Intent(AdminActivity.this,TambahSaldoActivity.class);
        startActivity(intent);
    }
    public void DeleteUser(View arg0){
        Intent intent = new Intent(AdminActivity.this,DeleteUserActivity.class);
        startActivity(intent);
    }
    public void Logout(View arg0){
        Intent intent = new Intent(AdminActivity.this,LoginActivity.class);
        intent.putStringArrayListExtra("KEY_NAME", null);
        startActivity(intent);
    }
}