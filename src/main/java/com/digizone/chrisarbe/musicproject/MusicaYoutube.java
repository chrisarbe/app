package com.digizone.chrisarbe.musicproject;

import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicaYoutube.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicaYoutube#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicaYoutube extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String PLACES_URL;
    String LOG_TAG;

    String[] values = new String[]{};
    String[] arr = {};

    static final String YOUTUBE_DATA_API_KEY = "AIzaSyCq8ylFId73K13bHZD6HxvWjEJOlsYQULI";
    String youtubeLink = "https://www.youtube.com/watch?v=";
    public static EditText campoSearch;

    String terminoBusqueda;
    private LinearLayout mainLayout;
    private ProgressBar mainProgressBar;

    private InterstitialAd mInterstitialAd;

    public MainActivity variable = new MainActivity();

    public MusicaYoutube() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicaYoutube.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicaYoutube newInstance(String param1, String param2) {
        MusicaYoutube fragment = new MusicaYoutube();
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

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-8744365861161319/1978590729");

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-8744365861161319/1978590729");
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("DC4FDD8F9668C1895E13BF225BFC8268").build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        busquedaRate();

        return inflater.inflate(R.layout.fragment_musica_youtube, container, false);
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

    public void busquedaRate () {
        PLACES_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet,id&maxResults=20&key=AIzaSyA5SjEcYREnw0bFHeZPa21wKAr6sox5j3s";
        LOG_TAG = "VolleyPlacesRemoteDS";

        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

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
                            Toast.makeText(getContext().getApplicationContext(), "JSON Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
        final ListView milista = (ListView)getView().findViewById(R.id.listaInicial);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, values);

        milista.setAdapter(adapter);


        milista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

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
                    Toast.makeText(getContext(), "Entre al if", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Entre al else", Toast.LENGTH_LONG).show();
                    //finish();
                }
                //startActivity(new Intent(MainActivity.this, YoutubeActivity.class));
                //Toast.makeText(getApplicationContext(), "Position: "+ item+" - Valor: "+itemval, Toast.LENGTH_LONG).show();
            }

        });
    }

    public void busquedaVideos (String busqueda) {
        PLACES_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet,id&maxResults=20&q="+busqueda+"&type=video&key=AIzaSyA5SjEcYREnw0bFHeZPa21wKAr6sox5j3s";
        LOG_TAG = "VolleyPlacesRemoteDS";

        // Instantiate the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

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
                            Toast.makeText(getContext().getApplicationContext(), "JSON Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    public AlertDialog createSearhDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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

    private void getYoutubeDownloadUrl(String youtubeLink) {
        new YouTubeExtractor(getActivity()) {

            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                //mainProgressBar.setVisibility(View.GONE);
                if (ytFiles == null) {
                    // Something went wrong we got no urls. Always check this.
                    getActivity().finish();
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
        Button btn = new Button(getContext());
        btn.setText(btnText);

        //getActivity().setContentView(R.layout.download_youtube);
        mainLayout = (LinearLayout) getView().findViewById(R.id.main_layout);

        btn.setOnClickListener(new View.OnClickListener() {

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
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
                variable.cargarFragment();
                //busquedaVideos(terminoBusqueda);

            }
        });
        try {
            mainLayout.addView(btn);
            Toast.makeText(getContext(), "Agregue boton", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext().getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName) {
        Uri uri = Uri.parse(youtubeDlUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(downloadTitle);

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public AlertDialog createDownloadYoutube() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.content_main, null);

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
