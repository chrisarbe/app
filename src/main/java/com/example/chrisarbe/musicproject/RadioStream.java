package com.example.chrisarbe.musicproject;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;


public class RadioStream extends AppCompatActivity {

    Button button_stop_piano;
    Button button_play_piano;

    Button button_stop_electronica;
    Button button_play_electronica;
    private String STREAM_URL ="http://148.72.152.10:1228/stream";

    private String STREAM_URL_ELE ="http://ibizaglobalradio.streaming-pro.com:8024/;";
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_stream);

        button_stop_piano=(Button) findViewById(R.id.button2);
        button_play_piano=(Button) findViewById(R.id.button1);

        button_stop_electronica=(Button) findViewById(R.id.button3);
        button_play_electronica=(Button) findViewById(R.id.button);
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
    }
}

