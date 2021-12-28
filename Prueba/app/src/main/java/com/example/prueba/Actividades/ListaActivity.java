package com.example.prueba.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba.BD.EntPokemon;
import com.example.prueba.BD.EntUsuario;
import com.example.prueba.BD.EntUsuvspoke;
import com.example.prueba.Entidades.Pokemon;
import com.example.prueba.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener, View.OnClickListener {

    private TableLayout tablaP;
    public ArrayList<Pokemon> pokemons;
    public static final String url = "https://pokeapi.co/api/v2/pokemon/";
    public ArrayList<CheckBox> checkBoxes;
    private Button favoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        tablaP = (TableLayout) findViewById(R.id.tablaP);
        tablaP.setStretchAllColumns(true);

        pokemons = new ArrayList<Pokemon>();
        checkBoxes = new ArrayList<CheckBox>();
        obtenerPokemons();

        favoritos = (Button) findViewById(R.id.favoritos);
        favoritos.setOnClickListener(this);

    }

    public void obtenerPokemons(){

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, this::onResponse, this::onErrorResponse);

        Volley.newRequestQueue(this).add(postRequest);

    }


    @Override
    public void onResponse(Object response) {

        try{
            JSONObject jsonObject = new JSONObject((String) response);
            JSONArray lista = (JSONArray) jsonObject.getJSONArray("results");

            for(int i =0; i< lista.length();i++){

                pokemons.add(new Pokemon(lista.getJSONObject(i).getString("name")));
                TableRow tableRow = new TableRow(ListaActivity.this);

                CheckBox checkBox = new CheckBox(ListaActivity.this);
                tableRow.addView(checkBox);

                TextView textView = new TextView(ListaActivity.this);
                textView.setText(lista.getJSONObject(i).getString("name"));
                tableRow.addView(textView);

                String urlDetalle = lista.getJSONObject(i).getString("url");
                StringRequest postRequest2 = new StringRequest(Request.Method.GET, urlDetalle, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject2 = new JSONObject((String) response);
                            JSONArray listaAbilities = (JSONArray) jsonObject2.getJSONArray("abilities");
                            JSONObject nameAbility = listaAbilities.getJSONObject(0).getJSONObject("ability");

                            TextView textView2 = new TextView(ListaActivity.this);
                            textView2.setText(nameAbility.getString("name"));
                            tableRow.addView(textView2);

                            TextView textView3 = new TextView(ListaActivity.this);
                            textView3.setText(jsonObject2.getString("base_experience"));
                            tableRow.addView(textView3);

                            TextView textView4 = new TextView(ListaActivity.this);
                            textView4.setText(jsonObject2.getString("weight"));
                            tableRow.addView(textView4);

                            EntPokemon entPokemon = new EntPokemon(getApplicationContext());

                            if(!entPokemon.pokemonExiste(textView.getText().toString())){
                                entPokemon.agregarPokemon(textView.getText().toString(),textView2.getText().toString(),Integer.parseInt(textView3.getText().toString()),Integer.parseInt(textView4.getText().toString()));
                            }

                            entPokemon.closebd();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.getMessage());
                    }
                });

                Volley.newRequestQueue(ListaActivity.this).add(postRequest2);

                if (getIntent().getExtras() != null) {
                    if (getIntent().getExtras().getString("usuario")!=null){
                        EntUsuvspoke entUsuvspoke = new EntUsuvspoke(getApplicationContext());
                        if(entUsuvspoke.esFavorito(getIntent().getExtras().getString("usuario"),textView.getText().toString())){
                            checkBox.setChecked(true);
                        }
                        entUsuvspoke.closebd();
                    }
                }

                checkBoxes.add(checkBox);
                tablaP.addView(tableRow);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error){
        Log.e("error",error.getMessage());
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.favoritos) {

            EntUsuvspoke entUsuvspoke = new EntUsuvspoke(getApplicationContext());

            for(int i = 0;i<pokemons.size();i++){

                if(checkBoxes.get(i).isChecked()){
                    if (getIntent().getExtras() != null) {
                        if (getIntent().getExtras().getString("usuario")!=null){
                            if(!entUsuvspoke.esFavorito(getIntent().getExtras().getString("usuario"),pokemons.get(i).getNombre())){
                                entUsuvspoke.agregarFavorito(getIntent().getExtras().getString("usuario"),pokemons.get(i).getNombre());
                            }
                        }
                    }
                }else{
                    if (getIntent().getExtras() != null) {
                        if (getIntent().getExtras().getString("usuario")!=null){
                            if(entUsuvspoke.esFavorito(getIntent().getExtras().getString("usuario"),pokemons.get(i).getNombre())){
                                entUsuvspoke.eliminarFavorito(getIntent().getExtras().getString("usuario"),pokemons.get(i).getNombre());
                            }
                        }
                    }
                }

            }

            Toast.makeText(getApplicationContext(), "Se actualizaron los favoritos", Toast.LENGTH_SHORT).show();


        }
    }
}