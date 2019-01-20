package com.digizone.chrisarbe.musicproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static android.widget.MediaController.*;


public class Reproductive extends AppCompatActivity implements MediaPlayerControl {
    MediaPlayer mediaPlayer = new MediaPlayer();
    String FilePath, urlAlbum;
    MediaController mediaController;
    Handler handler;
    ImageView imgAlbum;
    Reproductor variable = new Reproductor();
    int posRe = 0;//getIntent().getIntExtra("posicion",0);
    ArrayList<String> lista;
    public String[] arrPath2;//(String[]) getIntent().getSerializableExtra("arrPath");

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductive);

        imgAlbum = (ImageView)findViewById(R.id.imageView_Disco);
        Bundle bundle = this.getIntent().getExtras();
        FilePath = bundle.getString("Url");
        urlAlbum = bundle.getString("urlAlbum");
        imgUrlAlbum(urlAlbum);
        playAudio();


    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        //startActivity(new Intent(this, Reproductor.class));
        pause();
        finish();

    }

    private void imgUrlAlbum (String imgUrlAlbum){
        if (imgUrlAlbum == null){
            imgAlbum.setImageResource(R.drawable.logo);
        }else {
            File imgFile = new File(imgUrlAlbum);
            if (imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgAlbum.setImageBitmap(myBitmap);
            }
        }
    }

    private void playAudio(){
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(Reproductive.this);
        mediaController.setAnchorView(findViewById(R.id.audioView));
        handler = new Handler();
        try {
            mediaPlayer.setDataSource(FilePath);

            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (IOException e){
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mediaController.setEnabled(true);
                        mediaController.show();
                        mediaPlayer.start();
                    }
                });
            }
        });
        /*
        mediaController.setPrevNextListeners(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("forward button pressed");
                Object[] objNames = lista.toArray();
                String[] strNames = Arrays.copyOf(objNames, objNames.length, String[].class);
                try {
                    mediaPlayer.setDataSource(strNames[posRe+1]);
                    mediaPlayer.start();
                    mediaPlayer.prepare();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }, new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("backward button pressed");
                // v is mc
                // code for previous
            }
        });*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mediaController.show();
        return true;
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        int porcentage = (mediaPlayer.getCurrentPosition()*100)/mediaPlayer.getDuration();
        return porcentage;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        playAudio();
    }
}