package com.digizone.chrisarbe.musicproject;

import android.app.Dialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Reproductor.OnFragmentInteractionListener,
        MusicaYoutube.OnFragmentInteractionListener,
        RadioStreaming.OnFragmentInteractionListener,
        Home.OnFragmentInteractionListener{


    private InterstitialAd mInterstitialAd;

    static final String YOUTUBE_DATA_API_KEY = "AIzaSyCq8ylFId73K13bHZD6HxvWjEJOlsYQULI";

    Dialog customDialog = null;

    public static FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment = null;
        fragment = new Home();

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment).commit();
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

        Fragment fragment = null;
        Boolean fragment_selected = false;

        if (id == R.id.nav_camera) {
            fragment = new MusicaYoutube();
            fragment_selected = true;
        } else if (id == R.id.nav_gallery) {
            fragment = new Reproductor();
            fragment_selected = true;
        } else if (id == R.id.nav_slideshow) {
            //startActivity(new Intent(MainActivity.this, RadioStream.class));
            fragment = new RadioStreaming();
            fragment_selected = true;
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        if(fragment_selected){
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void cargarFragment () {
        Fragment fragment = new MusicaYoutube();
        getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment).commit();
    }
}
