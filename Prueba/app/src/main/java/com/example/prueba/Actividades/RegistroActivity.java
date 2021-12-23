package com.example.prueba.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prueba.BD.EntUsuario;
import com.example.prueba.R;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usuario;
    private EditText passwordRegistro;
    private Button registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        usuario = (EditText) findViewById(R.id.usuario);
        passwordRegistro = (EditText) findViewById(R.id.passwordRegistro);

        registrarse = (Button) findViewById(R.id.registrarse);
        registrarse.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registrarse) {

            EntUsuario entUsuario = new EntUsuario(getApplicationContext());

            if(entUsuario.usuarioExiste(usuario.getText().toString())){
                Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
            }else{
                entUsuario.agregarUsuario(usuario.getText().toString(), passwordRegistro.getText().toString());
                Toast.makeText(getApplicationContext(), "Se creo con exito", Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent();
                mIntent.setClass(getApplicationContext(), ListaActivity.class);
                startActivity(mIntent);
            }
            entUsuario.closebd();

        }
    }

}