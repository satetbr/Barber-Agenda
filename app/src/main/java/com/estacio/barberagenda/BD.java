package com.estacio.barberagenda;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BD {

     static SQLiteDatabase db=null;
     static Cursor cursor;

    public static void abrirBanco(Activity act){
        try{
            ContextWrapper cw = new ContextWrapper(act);
            db=cw.openOrCreateDatabase("Agenda",MODE_PRIVATE,null);
        }catch (Exception ex){
            Msg.mostrar("Erro ao abrir ou criar banco de dados", act);
        }
    }

    public static void abrirTabela(Activity act){
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS agendamentos (id INTEGER PRIMARY KEY, nome TEXT, fone TEXT, data DATE, hora DATETIME);");
        }catch (Exception ex){
            Msg.mostrar("Erro ao criar tabela", act);
        }
    }

    public static void fecharDB(){
        db.close();
    }

    public static void inserirRegistro(String nome, String telefone, String data, String horario, Activity act){
        abrirBanco(act);
        try {
            db.execSQL("INSERT INTO agendamentos (nome, fone, data, hora) VALUES ('"+nome+"','"+telefone+"','"+data+"','"+horario+"')");
        }catch (Exception ex){
            Msg.mostrar("Erro ao inserir o registro", act);
        }finally {
            Msg.mostrar("AGENDADO COM SUCESSO!", act);
        }
        fecharDB();
    }

    public static Cursor buscarDados(Activity act){
        abrirBanco(act);
        cursor=db.query("agendamentos",
                new String[]{"id","nome","fone","data","hora"},
                null,
                null,
                null,
                null,
                "data ASC, hora ASC",
                null
        );
        return cursor;
    }

    public static void excluirAgendamento(String id, Activity act){
        try {
            db.execSQL("DELETE FROM agendamentos WHERE id  = '"+id+"'");
        }catch (Exception ex){
            Msg.mostrar("ERRO AO EXCLUIR AGENDAMENTO", act);
        }finally {
            Msg.mostrar("EXCLUIDO COM SUCESSO", act);
        }
    }
}