package com.example.chrisarbe.musicproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public AlertDialog dialogSearch;

    public static EditText campoSearch;

    String[] values = new String[]{};

    String[] arr = {};

    String PLACES_URL;
    String LOG_TAG;

    static final String YOUTUBE_DATA_API_KEY = "AIzaSyCq8ylFId73K13bHZD6HxvWjEJOlsYQULI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSearch = createSearhDialog();
                dialogSearch.show();
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ayuda) {
            startActivity(new Intent(MainActivity.this, Ayuda.class));
        } else if (id == R.id.action_acercade) {
            startActivity(new Intent(MainActivity.this, AcercaDe.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this, YoutubeActivity.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public AlertDialog createSearhDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.fragment_search_music, null);

        campoSearch = (EditText) v.findViewById(R.id.campoBusqueda);

        builder.setView(v).setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.setView(v).setPositiveButton("Buscar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String termino = campoSearch.getText().toString();
                        //Toast.makeText(getApplicationContext(), "Termino : "+ termino, Toast.LENGTH_LONG).show();
                        busquedaVideos(termino);
                    }
                });
        return builder.create();
    }

    public void busquedaVideos (String busqueda) {
        PLACES_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet,id&q="+busqueda+"&type=video&key=AIzaSyA5SjEcYREnw0bFHeZPa21wKAr6sox5j3s";
        LOG_TAG = "VolleyPlacesRemoteDS";

        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Prepare the Request
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, //GET or POST
                PLACES_URL, //URL
                null, //Parameters
                new Response.Listener<JSONObject>() { //Listener OK

                    @Override
                    public void onResponse(JSONObject responsePlaces) {
                        //Response OK!! :)
                        try {
                            //JSONObject resultado = responsePlaces.getJSONObject("items");
                            //String cadena = responsePlaces.getString("items").substring(1, responsePlaces.getString("items").length() - 1);
                            //JSONObject result2 = new JSONObject(cadena);
                            JSONArray the_json_array = responsePlaces.getJSONArray("items");
                            Log.i("Este es el tamano ",the_json_array.toString());
                            int size = the_json_array.length();
                            Log.i("Este es el tamano",Integer.toString(size));
                            List<String> listFromArray = Arrays.asList(values);
                            List<String> tempList = new ArrayList<String>(listFromArray);
                            arr = Arrays.copyOf(arr, 5); // new size will be 10 elements
                            for (int i = 0; i < size; i++) {
                                JSONObject another_json_object = the_json_array.getJSONObject(i);
                                JSONObject result3 = new JSONObject(another_json_object.getString("snippet"));
                                tempList.add(result3.getString("title"));
                                JSONObject result4 = new JSONObject(another_json_object.getString("id"));
                                arr[i] = result4.getString("videoId");
                                //Toast.makeText(getApplicationContext(), "Hola: "+ result3.getString("title"), Toast.LENGTH_LONG).show();
                            }
                            Log.d(LOG_TAG,responsePlaces.toString());
                            String[] tempArray = new String[tempList.size()];
                            values = tempList.toArray(tempArray);
                            listaVideos();
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "JSON Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() { //Listener ERROR

            @Override
            public void onErrorResponse(VolleyError error) {
                //There was an error :(
                Log.d(LOG_TAG,error.toString());
            }
        });

        //Send the request to the requestQueue
        requestQueue.add(request);
    }

    public void listaVideos () {
        final ListView milista = (ListView)findViewById(R.id.listaInicial);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);

        milista.setAdapter(adapter);

        milista.setOnItemClickListener(new OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int item = position;
                String itemval = (String)milista.getItemAtPosition(position);
                YoutubeActivity.YOUTUBE_VIDEO_ID = arr[item];
                startActivity(new Intent(MainActivity.this, YoutubeActivity.class));
                Toast.makeText(getApplicationContext(), "Position: "+ item+" - Valor: "+itemval, Toast.LENGTH_LONG).show();
            }

        });
    }
}
