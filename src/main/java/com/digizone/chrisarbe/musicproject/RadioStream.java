package com.digizone.chrisarbe.musicproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;


public class RadioStream extends AppCompatActivity {

    Button button_stop_piano;
    Button button_play_piano;

    Button button_stop_electronica;
    Button button_play_electronica;

    Button button_stop_reggae;
    Button button_play_reggae;

    private String STREAM_URL ="http://148.72.152.10:1228/stream";

    private String STREAM_URL_ELE ="http://s3.sonicabroadcast.com:8635/;";
    private String STREAM_URL_REG ="http://streaming.turbo98.com:7020/;";
    private String STREAM_URL_ROCK ="http://108.61.20.171:10042/stream/;";
    private String STREAM_URL_SALSA ="http://192.99.17.12:6031/stream/;";
    private MediaPlayer mPlayer;

    String[] values = new String[]{};

    public int banderaPiano = 0;

    public int banderaElectronica = 0;

    public int banderaReggae = 0;

    public int banderaRock = 0;

    public int banderaSalsa = 0;

    private ProgressDialog pDialog;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_stream);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        builder = new AlertDialog.Builder(this);

        MobileAds.initialize(this, "ca-app-pub-8744365861161319~7639300880");

        AdView banner2 = (AdView) findViewById(R.id.banner2);
        AdRequest adRequest = new AdRequest.Builder().build();
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice("DC4FDD8F9668C1895E13BF225BFC8268").build();
        banner2.loadAd(adRequest);

        final ListView milista = (ListView)findViewById(R.id.lista_radio);

        values = new String[]{"Calm Radio - Solo Piano","Blue Marlin Ibiza Radio","Turbo 98 FM","Classic Rock 109","Salsa Warriors"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);

        milista.setAdapter(adapter);

        mPlayer = new MediaPlayer();

        milista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int item = position;
                String itemval = (String)milista.getItemAtPosition(position);
                if (item == 0 & banderaPiano == 0) {
                    pDialog.setMessage("Cargando Streaming ...");
                    showDialog();
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#28B463"));
                    parent.getChildAt(1).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(2).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(3).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(4).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    try{
                        mPlayer.reset();
                        mPlayer.setDataSource(STREAM_URL);
                        mPlayer.prepareAsync();
                        mPlayer.setOnPreparedListener(new MediaPlayer.
                                OnPreparedListener(){
                            @Override
                            public void onPrepared(MediaPlayer mp){
                                mp.start();
                                banderaPiano = 1;
                                hideDialog();
                            }
                        });
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else if (item == 0 & banderaPiano == 1) {
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    mPlayer.stop();
                    banderaPiano = 0;
                } else if (item == 1 & banderaElectronica == 0) {
                    pDialog.setMessage("Cargando Streaming ...");
                    showDialog();
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#28B463"));
                    parent.getChildAt(0).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(2).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(3).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(4).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    try{
                        mPlayer.reset();
                        mPlayer.setDataSource(STREAM_URL_ELE);
                        mPlayer.prepareAsync();
                        mPlayer.setOnPreparedListener(new MediaPlayer.
                                OnPreparedListener(){
                            @Override
                            public void onPrepared(MediaPlayer mp){
                                mp.start();
                                banderaElectronica = 1;
                                hideDialog();
                            }
                        });
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else if (item == 1 & banderaElectronica == 1) {
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    mPlayer.stop();
                    banderaElectronica = 0;
                } else if (item == 2 & banderaReggae == 0) {
                    pDialog.setMessage("Cargando Streaming ...");
                    showDialog();
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#28B463"));
                    parent.getChildAt(0).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(1).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(3).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(4).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    try{
                        mPlayer.reset();
                        mPlayer.setDataSource(STREAM_URL_REG);
                        mPlayer.prepareAsync();
                        mPlayer.setOnPreparedListener(new MediaPlayer.
                                OnPreparedListener(){
                            @Override
                            public void onPrepared(MediaPlayer mp){
                                mp.start();
                                banderaReggae = 1;
                                hideDialog();
                            }
                        });
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else if (item == 2 & banderaReggae == 1) {
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    mPlayer.stop();
                    banderaReggae = 0;
                } else if (item == 3 & banderaRock == 0) {
                    pDialog.setMessage("Cargando Streaming ...");
                    showDialog();
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#28B463"));
                    parent.getChildAt(0).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(1).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(2).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(4).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    try{
                        mPlayer.reset();
                        mPlayer.setDataSource(STREAM_URL_ROCK);
                        mPlayer.prepareAsync();
                        mPlayer.setOnPreparedListener(new MediaPlayer.
                                OnPreparedListener(){
                            @Override
                            public void onPrepared(MediaPlayer mp){
                                mp.start();
                                banderaRock = 1;
                                hideDialog();
                            }
                        });
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else if (item == 3 & banderaRock == 1) {
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    mPlayer.stop();
                    banderaRock = 0;
                } else if (item == 4 & banderaSalsa == 0) {
                    pDialog.setMessage("Cargando Streaming ...");
                    showDialog();
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#28B463"));
                    parent.getChildAt(0).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(1).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(2).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(3).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    try{
                        mPlayer.reset();
                        mPlayer.setDataSource(STREAM_URL_SALSA);
                        mPlayer.prepareAsync();
                        mPlayer.setOnPreparedListener(new MediaPlayer.
                                OnPreparedListener(){
                            @Override
                            public void onPrepared(MediaPlayer mp){
                                mp.start();
                                banderaSalsa = 1;
                                hideDialog();
                            }
                        });
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else if (item == 4 & banderaSalsa == 1) {
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    mPlayer.stop();
                    banderaSalsa = 0;
                }
                //Toast.makeText(getApplicationContext(), "Position: "+ item+" - Valor: "+itemval, Toast.LENGTH_LONG).show();
            }

        });

        /*
        button_stop_piano=(Button) findViewById(R.id.button2);
        button_play_piano=(Button) findViewById(R.id.button1);

        button_stop_electronica=(Button) findViewById(R.id.button3);
        button_play_electronica=(Button) findViewById(R.id.button);

        button_stop_reggae=(Button) findViewById(R.id.button5);
        button_play_reggae=(Button) findViewById(R.id.button4);

        mPlayer=new MediaPlayer();
        button_play_piano.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    mPlayer.reset();
                    mPlayer.setDataSource(STREAM_URL);
                    mPlayer.prepareAsync();
                    mPlayer.setOnPreparedListener(new MediaPlayer.
                            OnPreparedListener(){
                        @Override
                        public void onPrepared(MediaPlayer mp){
                            mp.start();
                        }
                    });
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        button_stop_piano.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPlayer.stop();
            }
        });

        button_play_electronica.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    mPlayer.reset();
                    mPlayer.setDataSource(STREAM_URL_ELE);
                    mPlayer.prepareAsync();
                    mPlayer.setOnPreparedListener(new MediaPlayer.
                            OnPreparedListener(){
                        @Override
                        public void onPrepared(MediaPlayer mp){
                            mp.start();
                        }
                    });
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        button_stop_electronica.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPlayer.stop();
            }
        });

        button_play_reggae.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try{
                    mPlayer.reset();
                    mPlayer.setDataSource(STREAM_URL_REG);
                    mPlayer.prepareAsync();
                    mPlayer.setOnPreparedListener(new MediaPlayer.
                            OnPreparedListener(){
                        @Override
                        public void onPrepared(MediaPlayer mp){
                            mp.start();
                        }
                    });
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        button_stop_reggae.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPlayer.stop();
            }
        });*/
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

