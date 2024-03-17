package com.estacio.barberagenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

public class tela_agendar extends AppCompatActivity {

    EditText et_numero, et_nome;
    DatePicker dp;
    TimePicker tp;
    Button bt_consultar, bt_agendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_agendar);
        Objects.requireNonNull(getSupportActionBar()).hide();

        et_numero = findViewById(R.id.et_numero);
        et_nome = findViewById(R.id.et_nome);

        tp = findViewById(R.id.tp);
        tp.setIs24HourView(true);
        dp =  findViewById(R.id.dp);
            dp.setMinDate(Calendar.getInstance().getTimeInMillis());

        bt_consultar = findViewById(R.id.bt_consultar);
        bt_agendar = findViewById(R.id.bt_agendar);

        BD.abrirBanco(this);
        BD.abrirTabela(this);
        BD.fecharDB();
    }

    public void inserirRegistro(View v){
        String st_nome, st_telefone, st_data, st_horario, dia, mes, ano, hora, minuto;
        st_nome = et_nome.getText().toString();
        st_telefone = et_numero.getText().toString();
        if (st_nome.isEmpty()){
            Msg.mostrar("PREENCHA O NOME DO CLIENTE", this);
            et_nome.requestFocus();
            return;
        }
        if (st_telefone.length() != 15){
            Msg.mostrar("PREENCHA O CAMPO TELEFONE CORRETAMENTE", this);
            et_numero.requestFocus();
            return;
        }
        dia = Integer.toString(dp.getDayOfMonth());
        mes = Integer.toString(dp.getMonth() + 1);
        ano = Integer.toString(dp.getYear());
        if (dp.getMonth() < 9) {
            mes = "0" + mes;
        }
        if (dp.getDayOfMonth() < 10){
            dia = "0" + dia;
        }
        st_data = dia + "/" + mes + "/" + ano;
        hora = Integer.toString(tp.getCurrentHour());
        minuto = Integer.toString(tp.getCurrentMinute());
        if (tp.getCurrentMinute() < 10){
            minuto = "0" + minuto;
        }
        if (tp.getCurrentHour() < 10){
            hora = "0" + hora;
        }
        st_horario = hora + ":" + minuto;
        BD.inserirRegistro(st_nome, st_telefone, st_data, st_horario, this);
        et_nome.setText(null);
        et_numero.setText((null));
        et_nome.requestFocus();
    }

    public void abrir_tela_consultar(View v) {
        Intent it_tela_consultar = new Intent(this, tela_consultar.class);
        startActivity(it_tela_consultar);
    }
    public void finalizar_agendar(View v) {
        this.finish();
    }
}