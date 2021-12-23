package com.example.prueba.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDProyectoPokemon extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NOMBRE="favoritos.db";
    public static final String TABLA_USUARIOS = "usuarios";
    public static final String TABLA_POKEMON = "pokemon";
    public static final String TABLA_USUVSPOKE = "ususvspoke";


    public BDProyectoPokemon(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLA_USUARIOS + "(" +
                "nombre TEXT PRIMARY KEY, " +
                "contrasena TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLA_POKEMON + "(" +
                "nombre TEXT PRIMARY KEY, " +
                "habilidad TEXT NOT NULL," +
                "experiencia INTEGER NOT NULL," +
                "peso INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLA_USUVSPOKE + "(" +
                "nombreusu TEXT, " +
                "nombrepoke TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + TABLA_USUARIOS);
        db.execSQL("DROP TABLE " + TABLA_POKEMON);
        db.execSQL("DROP TABLE " + TABLA_USUVSPOKE);
        onCreate(db);

    }
}
