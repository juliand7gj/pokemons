package com.example.prueba.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.prueba.BD.BDProyectoPokemon;

public class EntUsuvspoke {

    SQLiteDatabase BDSQ = null;
    public BDProyectoPokemon BD = null;

    public void closebd()
    {
        BDSQ.close();
    }

    public EntUsuvspoke(Context getServer) {
        BD = new BDProyectoPokemon(getServer);
        BDSQ = BD.getWritableDatabase();
    }

    public boolean esFavorito(String usuario, String pokemon){

        boolean existe = false;
        Cursor cursor 	= null;
        String sql = "SELECT * FROM ususvspoke where nombreusu='"+usuario+"' and "+"nombrepoke='"+pokemon+"'";
        cursor 			= BDSQ.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            existe = true;
        }

        cursor.close();

        return existe;
    }

    public void agregarFavorito(String usuario, String pokemon){

        String sql = "INSERT INTO ususvspoke(nombreusu,nombrepoke) " +
                "VALUES('"+usuario+"','"+pokemon+"');";
        BDSQ.execSQL(sql);

    }

    public void eliminarFavorito(String usuario, String pokemon){
        String sql = "DELETE FROM ususvspoke where nombreusu='"+usuario+"' and "+"nombrepoke='"+pokemon+"'";
        BDSQ.execSQL(sql);
    }



}
