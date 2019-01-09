package com.digizone.chrisarbe.musicproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Timer;
import java.util.TimerTask;

public class Inicio extends AppCompatActivity {

    private SQLiteHandler dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        dbHelper = new SQLiteHandler(getApplicationContext());

        String selectQuery = "SELECT * FROM user";
        database  = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery(selectQuery, null);

        if(c.moveToFirst()){
            do{

            }while(c.moveToNext());
        } else {
            dbHelper.addUser("musiczone@digizone.com", "123456", "0");
        }
        c.close();
        database.close();

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                dbHelper = new SQLiteHandler(getApplicationContext());

                String selectQuery = "SELECT * FROM user";
                database  = dbHelper.getReadableDatabase();
                Cursor c = database.rawQuery(selectQuery, null);

                if(c.moveToFirst()){
                    do{
                        if (c.getString(c.getColumnIndex("flag")).equals("1")){
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }while(c.moveToNext());
                }
                c.close();
                database.close();



                /*
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();*/
            }
        },3000);


    }
}
