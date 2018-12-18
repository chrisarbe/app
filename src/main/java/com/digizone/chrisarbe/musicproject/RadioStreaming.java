package com.digizone.chrisarbe.musicproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RadioStreaming.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RadioStreaming#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RadioStreaming extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private String STREAM_URL ="http://148.72.152.10:1228/stream";

    private String STREAM_URL_ELE ="http://s3.sonicabroadcast.com:8635/;";
    private String STREAM_URL_REG ="http://streaming.turbo98.com:7020/;";
    private String STREAM_URL_ROCK ="http://108.61.20.171:10042/stream/;";
    private String STREAM_URL_SALSA ="http://192.99.17.12:6031/stream/;";
    private String STREAM_URL_LATIN =  "http://144.217.64.13:7000/stream/;";

    private MediaPlayer mPlayer;

    String[] values = new String[]{};

    public int banderaPiano = 0;

    public int banderaElectronica = 0;

    public int banderaReggae = 0;

    public int banderaRock = 0;

    public int banderaSalsa = 0;

    public int banderaDiscoDescargar = 0;

    private ProgressDialog pDialog;

    private AlertDialog.Builder builder;

    public RadioStreaming() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RadioStreaming.
     */
    // TODO: Rename and change types and number of parameters
    public static RadioStreaming newInstance(String param1, String param2) {
        RadioStreaming fragment = new RadioStreaming();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_radio_streaming, container, false);

        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        builder = new AlertDialog.Builder(getContext());

        MobileAds.initialize(getContext(), "ca-app-pub-8744365861161319~7639300880");

        AdView banner2 = (AdView) rootView.findViewById(R.id.banner2);
        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("DC4FDD8F9668C1895E13BF225BFC8268").build();
        banner2.loadAd(adRequest);

        final ListView milista = (ListView)rootView.findViewById(R.id.lista_radio);

        values = new String[]{"Calm Radio - Solo Piano","Blue Marlin Ibiza Radio","Turbo 98 FM","Classic Rock 109","Salsa Warriors","Suave 107 FM"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, values);

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
                    parent.getChildAt(5).setBackgroundColor(Color.parseColor("#FFFFFF"));
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
                    parent.getChildAt(5).setBackgroundColor(Color.parseColor("#FFFFFF"));
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
                    parent.getChildAt(5).setBackgroundColor(Color.parseColor("#FFFFFF"));
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
                    parent.getChildAt(5).setBackgroundColor(Color.parseColor("#FFFFFF"));
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
                    parent.getChildAt(5).setBackgroundColor(Color.parseColor("#FFFFFF"));
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
                } else if (item == 5 & banderaDiscoDescargar == 0) {
                    pDialog.setMessage("Cargando Streaming ...");
                    showDialog();
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#28B463"));
                    parent.getChildAt(0).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(1).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(2).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(3).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    parent.getChildAt(4).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    try{
                        mPlayer.reset();
                        mPlayer.setDataSource(STREAM_URL_LATIN);
                        mPlayer.prepareAsync();
                        mPlayer.setOnPreparedListener(new MediaPlayer.
                                OnPreparedListener(){
                            @Override
                            public void onPrepared(MediaPlayer mp){
                                mp.start();
                                banderaDiscoDescargar = 1;
                                hideDialog();
                            }
                        });
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                } else if (item == 5 & banderaDiscoDescargar == 1) {
                    parent.getChildAt(item).setBackgroundColor(Color.parseColor("#FFFFFF"));
                    mPlayer.stop();
                    banderaDiscoDescargar = 0;
                }
                //Toast.makeText(getApplicationContext(), "Position: "+ item+" - Valor: "+itemval, Toast.LENGTH_LONG).show();
            }

        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
