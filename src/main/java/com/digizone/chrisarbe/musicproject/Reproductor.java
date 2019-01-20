package com.digizone.chrisarbe.musicproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;

import static android.provider.MediaStore.Audio.Albums.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Reproductor.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Reproductor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reproductor extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private InterstitialAd mInterstitialAd;

    public FloatingActionButton home;

    public Reproductor() {
        // Required empty public constructor
    }

    Button play_pause, btn_repetir, btn_anterior,btn_siguiente,btn_stop;
    MediaPlayer mp;
    int repetir = 2, posicion = 0;
    boolean sonando;
    int sonandoPos;
    String[] values = new String[]{};
    String[] item = new String[]{};

    MediaPlayer vectormp [];

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////CAMBIOS REVISAR O ELIMINAR/////////////////////////////////////////////////////////////////////////////

    private int count, i, TRACK_Column, ID_Column, DATA_Column, YEAR_Column, DURATION_Column, ALBUM_ID_Column, ALBUN_Column, ARTIST_Column;
    private int [] idMusic;
    TextView title2, artist, time; //pendiente de tiitle2 por solo title.........
    public String[] audioLista, artistLista, arrPath,musicTime, artistaAlbumLista;
    ListView lista;
    ImageView albumImg;
    String urlAlbum;
    public int posGlobal;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1 ;


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////// FIN DE LOS CAMBIOS REVISAR O ELIMINAR/////////////////////////////////////////////////////////////////

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reproductor.
     */
    // TODO: Rename and change types and number of parameters
    public static Reproductor newInstance(String param1, String param2) {
        Reproductor fragment = new Reproductor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_reproductor, container, false);

        home = (FloatingActionButton) rootview.findViewById(R.id.fab2);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment = new Home();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor, fragment).commit();
            }
        });

        values = new String[]{};
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////CAMBIOS REVISAR O ELIMINAR/////////////////////////////////////////////////////////////////////////////

        lista = (ListView) rootview.findViewById(R.id.listView_Lista);
        audioCursor();
        AudioAdapter audioAdapter = new AudioAdapter();
        lista.setAdapter(audioAdapter);
        return rootview;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////CAMBIOS REVISAR O ELIMINAR/////////////////////////////////////////////////////////////////////////////

    private void audioCursor(){
        String [] information ={
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TRACK,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_KEY,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.TITLE_KEY,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.ARTIST
        };

        final String orderBay = MediaStore.Audio.Media._ID;
        Cursor audioCursor = getContext().getContentResolver().query(
                //EXTERNAL_CONTENT_URI para el volumen  de almacenamiento
                //"primaria" externo.
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, information, null, null, orderBay);
        count = audioCursor.getCount();
        audioLista = new String[count];
        artistLista = new String[count];
        arrPath = new String[count];
        musicTime = new String[count];
        artistaAlbumLista = new String[count];


        ID_Column = audioCursor.getColumnIndex(MediaStore.Audio.Media._ID);
        DATA_Column = audioCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        YEAR_Column = audioCursor.getColumnIndex(MediaStore.Audio.Media.YEAR);
        DURATION_Column = audioCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        ALBUM_ID_Column = audioCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        ALBUN_Column = audioCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        TRACK_Column = audioCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        ARTIST_Column = audioCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

        while (audioCursor.moveToNext()){
            audioLista[i] = audioCursor.getString(TRACK_Column);
            artistLista[i] = audioCursor.getString(ARTIST_Column);
            arrPath[i] = audioCursor.getString(DATA_Column);
            musicTime[i] = audioCursor.getString(DURATION_Column);
            artistaAlbumLista[i] = audioCursor.getString(ALBUM_ID_Column);
            i++;
        }

        audioCursor.close();
    }



    public class AudioAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        public AudioAdapter(){
            inflater = (LayoutInflater) getContext().getSystemService(
                    //utilizar el getSystemService(String) para recuperar un LayoutInflater para inflar los recursosde diseño en este contexto.
                    Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.rows,null);
            title2 = (TextView) view.findViewById(R.id.textView_Title);
            artist = (TextView) view.findViewById(R.id.textView_Artist);
            time = (TextView) view.findViewById(R.id.textView_Time);
            albumImg = (ImageView) view.findViewById(R.id.imageView_Pista);

            title2.setId(i);
            artist.setId(i);
            albumImg.setId(i);


            title2.setText(audioLista[i]);
            artist.setText(artistLista[i]);
            long tmp = Integer.parseInt(musicTime[i]);
            time.setText(convertDuration(tmp));
            String artitsAlbum = artistaAlbumLista[i];
            String urlAlbum = urlAlbunArt(artitsAlbum);
            imgUrlAlbum(urlAlbum);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {


                    String url = arrPath[pos];
                    String artitsAlbum = artistaAlbumLista[pos];
                    String urlAlbum = urlAlbunArt(artitsAlbum);

                    Intent intent = (new Intent(getActivity(), Reproductive.class));
                    intent.putExtra("Url",url);
                    intent.putExtra("urlAlbum",urlAlbum);
                    startActivity(intent);

                }
            });

            return view;
        }

        private void imgUrlAlbum (String imgUrlAlbum){
            if (imgUrlAlbum == null){
                albumImg.setImageResource(R.drawable.logo);
            }else {
                File imgFile = new File(imgUrlAlbum);
                if (imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    albumImg.setImageBitmap(myBitmap);
                }
            }
        }

        public String convertDuration(long duration){
            String out = null;
            long hours = 0;
            try{
                hours = (duration/3600000);
            }catch (Exception e){
                e.printStackTrace();
                return out;
            }

            long remaining_minutes =(duration - (hours * 3600000))/60000;
            String minutes = String.valueOf(remaining_minutes);
            if(minutes.equals(0)){
                minutes = "00";
            }
            long remaining_seconds = (duration - (hours * 3600000) - (remaining_minutes * 60000));
            String seconds = String.valueOf(remaining_seconds);
            if(seconds.length() < 2){
                seconds = "00";
            }else{
                seconds = seconds.substring(0,2);
            }
            if(hours > 0){
                out = hours + ":" + minutes + ":" + seconds;
            }else{
                out = minutes + ":" + seconds;
            }
            return out;
        }

        private String urlAlbunArt(String artistAlbum){
            String[] projection = new  String[]{ALBUM_ART};
            String selection = _ID + "=?";
            String[] selectionArt = new String[]{artistAlbum};
            Cursor cursor = getActivity().getContentResolver().query(EXTERNAL_CONTENT_URI,projection,selection,selectionArt,null);
            String urlAlbum = "";
            if(cursor != null){
                if (cursor.moveToFirst()){
                    urlAlbum = cursor.getString(cursor.getColumnIndexOrThrow(ALBUM_ART));
                }
                cursor.close();
            }
            return urlAlbum;
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////FIN DE LOS CAMBIOS REVISAR O ELIMINAR/////////////////////////////////////////////////////////////////////////////

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void PlayPause(View view, int pos){
        if(vectormp [pos].isPlaying()){
            vectormp[pos].pause();
            play_pause.setBackgroundResource(R.drawable.play_circle);
            //Toast.makeText(getContext(),"Pause",Toast.LENGTH_SHORT).show();
        }else {
            try {
                //vectormp[pos].
                vectormp[pos].prepareAsync();
                vectormp[pos].prepare();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
            vectormp[pos].start();
            play_pause.setBackgroundResource(R.drawable.stop);
            //Toast.makeText(getContext(),"Play",Toast.LENGTH_SHORT).show();
        }
    }

    public void Stop(View view, int pos){
        if(vectormp[pos] != null){
            vectormp[pos].stop();

            play_pause.setBackgroundResource(R.drawable.play_circle);
            //iv.setImageResource(R.drawable.ic_looks_one_black_48dp);
            //Toast.makeText(getContext(),"Stop",Toast.LENGTH_SHORT).show();
        }
    }

    public void Repetir(View view){
        if(repetir == 1){
            btn_repetir.setBackgroundResource(R.drawable.ic_menu_gallery);
            Toast.makeText(getContext(),"No Repetir",Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(false);
            repetir = 2;
        }else {
            btn_repetir.setBackgroundResource(R.drawable.ic_menu_camera);
            Toast.makeText(getContext(),"Repetir",Toast.LENGTH_SHORT).show();
            vectormp[posicion].setLooping(true);
            repetir = 1;
        }
    }

    public void Siguiente (View view){

        if(posicion < vectormp.length -1){
            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();
                posicion++;
                vectormp[posicion].start();

            }else{
                posicion++;
            }
        }else {
            Toast.makeText(getContext(),"No Hay Mas Canciones",Toast.LENGTH_SHORT).show();
        }
    }

    public void Anterior (View view){
        if(posicion >= 1){
            if(vectormp[posicion].isPlaying()){
                vectormp[posicion].stop();

                posicion--;

                vectormp[posicion].start();

            }else{
                posicion--;
            }
        }else{
            Toast.makeText(getContext(),"No Hay Más Canciones",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}