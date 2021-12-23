package com.example.prueba.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prueba.BD.BDProyectoPokemon;
import com.example.prueba.BD.EntUsuario;
import com.example.prueba.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button iniciar;
    private EditText login;
    private EditText password;
    private Button registrar;
    public BDProyectoPokemon dbProyectoPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbProyectoPokemon = new BDProyectoPokemon(this);
        SQLiteDatabase db = dbProyectoPokemon.getWritableDatabase();


        iniciar = (Button) findViewById(R.id.iniciar);
        iniciar.setOnClickListener(this);

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);

        registrar = (Button) findViewById(R.id.registrar);
        registrar.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.iniciar) {

            EntUsuario entUsuario = new EntUsuario(getApplicationContext());

            if(entUsuario.usuarioExiste(login.getText().toString())){
                if(entUsuario.contrasenaValida(login.getText().toString(),password.getText().toString())){
                    Intent mIntent = new Intent();
                    mIntent.setClass(getApplicationContext(), ListaActivity.class);
                    mIntent.putExtra("usuario", login.getText().toString());
                    startActivity(mIntent);
                }else{
                    Toast.makeText(getApplicationContext(), "Contraseña invalida", Toast.LENGTH_SHORT).show();

                }

            }else{
                Toast.makeText(getApplicationContext(), "No existe el usuario", Toast.LENGTH_SHORT).show();
            }

            entUsuario.closebd();

        }else if (v.getId() == R.id.registrar){
            Intent mIntent = new Intent();
            mIntent.setClass(getApplicationContext(), RegistroActivity.class);
            startActivity(mIntent);
        }

    }
}