package com.estacio.barberagenda;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class tela_consultar extends AppCompatActivity {

    TextView tv_nome, tv_telefone, tv_data, tv_hora;
    Button bt_anterior, bt_proximo, bt_excluir;

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_consultar);
        Objects.requireNonNull(getSupportActionBar()).hide();

        tv_nome = findViewById(R.id.tv_nome);
        tv_telefone = findViewById(R.id.tv_telefone);
        tv_data = findViewById(R.id.tv_data);
        tv_hora = findViewById(R.id.tv_hora);

        bt_anterior = findViewById(R.id.bt_anterior);
        bt_proximo = findViewById(R.id.bt_proximo);
        bt_excluir = findViewById(R.id.bt_excluir);

        cursor=BD.buscarDados(this);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            mostrarDados();
        }else{
            Msg.mostrar("NENHUM AGENDAMENTO ENCONTRADO", this);
        }
    }


    public void mostrarDados(){
        tv_nome.setText(cursor.getString(cursor.getColumnIndex("nome")));
        tv_telefone.setText(cursor.getString(cursor.getColumnIndex("fone")));
        tv_data.setText(cursor.getString(cursor.getColumnIndex("data")));
        tv_hora.setText(cursor.getString(cursor.getColumnIndex("hora")));
    }

    public void proximoAgendado(View v){
        try {
            cursor.moveToNext();
            mostrarDados();
        }catch (Exception ex){
            if(cursor.isAfterLast()){
                cursor.moveToLast();
                Msg.mostrar("NÃO EXISTEM MAIS AGENDADOS", this);
            }else{
                Msg.mostrar("ERRO AO NAVEGAR", this);
            }
        }
    }

    public void agendadoAnterior(View v){
        try {
            cursor.moveToPrevious();
            mostrarDados();
        }catch (Exception ex){
            if(cursor.isBeforeFirst()){
                cursor.moveToFirst();
                Msg.mostrar("NÃO EXISTEM MAIS AGENDADOS", this);
            }else{
                Msg.mostrar("ERRO AO NAVEGAR", this);
            }
        }
    }

    public void finalizar_consultar(View v) {
        this.finish();
    }

    public void excluir(View v){
        String id = cursor.getString(cursor.getColumnIndex("id"));
        BD.excluirAgendamento(id, this);
        cursor=BD.buscarDados(this);
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            mostrarDados();
        }else{
            Msg.mostrar("NENHUM AGENDAMENTO ENCONTRADO", this);
        }
    }
}