package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class ListaActivity extends AppCompatActivity {

    private TableLayout tablaP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        tablaP = (TableLayout) findViewById(R.id.tablaP);
        tablaP.setStretchAllColumns(true);
        obtenerPokemons();
    }

    private void obtenerPokemons(){
        String url = "https://pokeapi.co/api/v2/pokemon/";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray lista = (JSONArray) jsonObject.getJSONArray("results");

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



                        System.out.println("Lista: "+lista.getJSONObject(i).getString("name"));
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