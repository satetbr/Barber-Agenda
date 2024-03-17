package com.estacio.barberagenda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void abrir_tela_agendar(View v){
        Intent it_tela_agendar = new Intent(this, tela_agendar.class);
        startActivity(it_tela_agendar);
    }
}