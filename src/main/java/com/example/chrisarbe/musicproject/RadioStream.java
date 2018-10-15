package com.example.chrisarbe.musicproject;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

    private String STREAM_URL_ELE ="http://ibizaglobalradio.streaming-pro.com:8024/;";
    private String STREAM_URL_REG ="http://listen.radionomy.com/ledjamradio.mp3";
    private MediaPlayer mPlayer;

    String[] values = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_stream);

        MobileAds.initialize(this, "ca-app-pub-8744365861161319~7639300880");

        AdView banner2 = (AdView) findViewById(R.id.banner2);
        AdRequest adRequest = new AdRequest.Builder().build();
        banner2.loadAd(adRequest);

        final ListView milista = (ListView)findViewById(R.id.lista_radio);

        values = new String[]{"Piano","Electrónica","Reggae","Blue"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);

        milista.setAdapter(adapter);

        mPlayer = new MediaPlayer();

        milista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int item = position;
                String itemval = (String)milista.getItemAtPosition(position);
                if (item == 0) {
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
                Toast.makeText(getApplicationContext(), "Position: "+ item+" - Valor: "+itemval, Toast.LENGTH_LONG).show();
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
}

