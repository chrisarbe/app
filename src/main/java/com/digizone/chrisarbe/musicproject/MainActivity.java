package com.digizone.chrisarbe.musicproject;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.util.SortedList;
import android.util.Log;
import android.util.SparseArray;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
//import com.commit451.youtubeextractor.YouTubeExtractionResult;
//import com.commit451.youtubeextractor.YouTubeExtractor;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;


import static android.widget.AdapterView.*;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    private InterstitialAd mInterstitialAd;

    public AlertDialog dialogSearch;

    public static EditText campoSearch;

    String[] values = new String[]{};

    String[] arr = {};

    String PLACES_URL;
    String LOG_TAG;

    String terminoBusqueda;

    static final String YOUTUBE_DATA_API_KEY = "AIzaSyCq8ylFId73K13bHZD6HxvWjEJOlsYQULI";

    String youtubeLink = "https://www.youtube.com/watch?v=";

    Dialog customDialog = null;

    private LinearLayout mainLayout;
    private ProgressBar mainProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8744365861161319/1978590729");

        MobileAds.initialize(this,
                "ca-app-pub-8744365861161319~7639300880");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8744365861161319/1978590729");
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("DC4FDD8F9668C1895E13BF225BFC8268").build());

        MobileAds.initialize(this, "ca-app-pub-8744365861161319~7639300880");

        AdView banner1 = (AdView) findViewById(R.id.banner1);
        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("DC4FDD8F9668C1895E13BF225BFC8268").build();
        banner1.loadAd(adRequest);

        busquedaRate();

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

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            startActivity(new Intent(MainActivity.this, RadioStream.class));
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

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
                        terminoBusqueda = termino;
                        //Toast.makeText(getApplicationContext(), "Termino : "+ termino, Toast.LENGTH_LONG).show();
                        busquedaVideos(termino);
                    }
                });
        return builder.create();
    }

    public void busquedaVideos (String busqueda) {
        PLACES_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet,id&maxResults=20&q="+busqueda+"&type=video&key=AIzaSyA5SjEcYREnw0bFHeZPa21wKAr6sox5j3s";
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
                            values = new String[]{};
                            List<String> listFromArray = Arrays.asList(values);
                            List<String> tempList = new ArrayList<String>(listFromArray);
                            arr = Arrays.copyOf(arr, 20); // new size will be 10 elements
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

    public void busquedaRate () {
        PLACES_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet,id&maxResults=20&key=AIzaSyA5SjEcYREnw0bFHeZPa21wKAr6sox5j3s";
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
                            values = new String[]{};
                            List<String> listFromArray = Arrays.asList(values);
                            List<String> tempList = new ArrayList<String>(listFromArray);
                            arr = Arrays.copyOf(arr, 20); // new size will be 10 elements
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
                /*
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                */
                int item = position;
                String itemval = (String)milista.getItemAtPosition(position);
                YoutubeActivity.YOUTUBE_VIDEO_ID = arr[item];

                youtubeLink = youtubeLink + arr[item];

                if (youtubeLink != null && (youtubeLink.contains("://youtu.be/") || youtubeLink.contains("youtube.com/watch?v="))) {
                    // We have a valid link
                    getYoutubeDownloadUrl(youtubeLink);
                    //Toast.makeText(this, "Hola Mundo 1", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(this, "Hola Mundo 2", Toast.LENGTH_LONG).show();
                    //finish();
                }
                //startActivity(new Intent(MainActivity.this, YoutubeActivity.class));
                //Toast.makeText(getApplicationContext(), "Position: "+ item+" - Valor: "+itemval, Toast.LENGTH_LONG).show();
            }

        });
    }


   private void getYoutubeDownloadUrl(String youtubeLink) {
        new YouTubeExtractor(this) {

            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                //mainProgressBar.setVisibility(View.GONE);

                setContentView(R.layout.activity_main);
                mainLayout = (LinearLayout) findViewById(R.id.main_layout);

                if (ytFiles == null) {
                    // Something went wrong we got no urls. Always check this.
                    finish();
                    return;
                }
                // Iterate over itags
                for (int i = 0, itag; i < ytFiles.size(); i++) {
                    itag = ytFiles.keyAt(i);
                    // ytFile represents one file with its url and meta data
                    YtFile ytFile = ytFiles.get(itag);

                    // Just add videos in a decent format => height -1 = audio
                    if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 360) {
                        addButtonToMainLayout(vMeta.getTitle(), ytFile);
                    }
                }
            }
        }.extract(youtubeLink, true, false);
        //dialogSearch = createDownloadYoutube();
        //dialogSearch.show();
    }

    private void addButtonToMainLayout(final String videoTitle, final YtFile ytfile) {
        // Display some buttons and let the user choose the format
        String btnText = (ytfile.getFormat().getHeight() == -1) ? "Audio " + ytfile.getFormat().getAudioBitrate() + " kbit/s" : ytfile.getFormat().getHeight() + "p";
        btnText += (ytfile.getFormat().isDashContainer()) ? " dash" : "";
        Button btn = new Button(this);
        btn.setText(btnText);

        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String filename;
                if (videoTitle.length() > 55) {
                    filename = videoTitle.substring(0, 55) + "." + ytfile.getFormat().getExt();
                } else {
                    filename = videoTitle + "." + ytfile.getFormat().getExt();
                }
                filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");
                downloadFromUrl(ytfile.getUrl(), videoTitle, filename);
                //finish();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                busquedaVideos(terminoBusqueda);

            }
        });
        try {
            mainLayout.addView(btn);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName) {
        Uri uri = Uri.parse(youtubeDlUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(downloadTitle);

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public AlertDialog createDownloadYoutube() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.activity_sample_download, null);

        // Check how it was started and if we can get the youtube link
        /*
        if (youtubeLink != null && (youtubeLink.contains("://youtu.be/") || youtubeLink.contains("youtube.com/watch?v="))) {
            // We have a valid link
            getYoutubeDownloadUrl(youtubeLink);
            Toast.makeText(this, "Hola Mundo 1", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Hola Mundo 2", Toast.LENGTH_LONG).show();
            //finish();
        }
        */
        builder.setView(v).setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

}
