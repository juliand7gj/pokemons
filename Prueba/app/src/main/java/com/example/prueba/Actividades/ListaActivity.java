package com.example.prueba.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prueba.BD.EntPokemon;
import com.example.prueba.BD.EntUsuario;
import com.example.prueba.Entidades.Pokemon;
import com.example.prueba.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    private TableLayout tablaP;
    public ArrayList<Pokemon> pokemons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        tablaP = (TableLayout) findViewById(R.id.tablaP);
        tablaP.setStretchAllColumns(true);

        pokemons = new ArrayList<Pokemon>();
        obtenerPokemons();

    }

    public void obtenerPokemons(){
        String url = "https://pokeapi.co/api/v2/pokemon/";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray lista = (JSONArray) jsonObject.getJSONArray("results");

                    EntPokemon entPokemon = new EntPokemon(getApplicationContext());

                    for(int i =0; i< lista.length();i++){

                        TableRow tableRow = new TableRow(ListaActivity.this);

                        TextView textView = new TextView(ListaActivity.this);
                        textView.setText(lista.getJSONObject(i).getString("name"));
                        tableRow.addView(textView);

                        String urlDetalle = lista.getJSONObject(i).getString("url");
                        StringRequest postRequest2 = new StringRequest(Request.Method.GET, urlDetalle, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jsonObject2 = new JSONObject(response);
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

                                    if(!entPokemon.pokemonExiste(textView.getText().toString())){
                                        entPokemon.agregarPokemon(textView.getText().toString(),textView2.getText().toString(),Integer.parseInt(textView3.getText().toString()),Integer.parseInt(textView4.getText().toString()));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){
                                Log.e("error",error.getMessage());
                            }
                        });

                        Volley.newRequestQueue(ListaActivity.this).add(postRequest2);

                        tablaP.addView(tableRow);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("error",error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(postRequest);

    }


}