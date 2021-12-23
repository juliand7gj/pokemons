package com.example.prueba.BD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EntUsuario {

    SQLiteDatabase BDSQ = null;
    public BDProyectoPokemon BD = null;

    public void closebd()
    {
        BDSQ.close();
    }

    public EntUsuario(Context getServer) {
        BD = new BDProyectoPokemon(getServer);
        BDSQ = BD.getWritableDatabase();
    }

    public boolean usuarioExiste(String usuario){

        boolean existe = false;
        Cursor cursor 	= null;
        String sql = "SELECT * FROM usuarios where nombre='"+usuario+"'";
        cursor 			= BDSQ.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            existe = true;
        }

        cursor.close();

        return existe;
    }

    public void agregarUsuario(String nombre, String contrasena){

        String sql = "INSERT INTO USUARIOS(nombre,contrasena) " +
                "VALUES('"+nombre+"','"+contrasena+"');";
        BDSQ.execSQL(sql);

    }

    public boolean contrasenaValida(String nombre, String contrasena){
        boolean correcta = false;
        Cursor cursor 	= null;
        String[][] dato = null;
        String sql = "SELECT * FROM usuarios where nombre='"+nombre+"'";
        cursor 			= BDSQ.rawQuery(sql,null);


        dato = new String[cursor.getCount()][2];
        int i = 0;
        while (cursor.moveToNext()){
            dato[i][0] = cursor.getString(0);
            dato[i][1] = cursor.getString(1);
            if(cursor.getString(1).toString().equals(contrasena)){
                correcta=true;
            }
            i++;
        }
        cursor.close();

        return correcta;
    }

}
