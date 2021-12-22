package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button iniciar = null;
    private EditText login = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        iniciar = (Button) findViewById(R.id.iniciar);
        iniciar.setOnClickListener(this);

        login = (EditText) findViewById(R.id.login);

    }


    @Override
    public void onClick(View v) {



        if (v.getId() == R.id.iniciar) {

            if(login.getText().toString().equals("julian")){
                Intent mIntent = new Intent();
                mIntent.setClass(getApplicationContext(), ListaActivity.class);
                startActivity(mIntent);
            }else{
                Toast.makeText(getApplicationContext(), "Error de usuario", Toast.LENGTH_SHORT).show();
            }



        }

    }
}