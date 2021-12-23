package com.example.prueba.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EntPokemon {

    SQLiteDatabase BDSQ = null;
    public BDProyectoPokemon BD = null;

    public void closebd()
    {
        BDSQ.close();
    }

    public EntPokemon(Context getServer) {
        BD = new BDProyectoPokemon(getServer);
        BDSQ = BD.getWritableDatabase();
    }

    public boolean pokemonExiste(String pokemon){

        boolean existe = false;
        Cursor cursor 	= null;
        String sql = "SELECT * FROM pokemon where nombre='"+pokemon+"'";
        cursor 			= BDSQ.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            existe = true;
        }

        cursor.close();

        return existe;
    }

    public void agregarPokemon(String nombre, String habilidad, int experiencia, int peso){

        String sql = "INSERT INTO POKEMON(nombre,habilidad,experiencia, peso) " +
                "VALUES('"+nombre+"','"+habilidad+"',"+experiencia+","+peso+");";
        BDSQ.execSQL(sql);

    }

}
