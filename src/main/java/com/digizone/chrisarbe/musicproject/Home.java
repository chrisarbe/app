package com.digizone.chrisarbe.musicproject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.util.RangeValueIterator;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
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

    public AlertDialog dialogSearch;

    private ProgressDialog pDialog;

    private LinearLayout mainLayout;

    static final String YOUTUBE_DATA_API_KEY = "AIzaSyCq8ylFId73K13bHZD6HxvWjEJOlsYQULI";
    String youtubeLink;

    Dialog customDialog = null;

    List<HashMap<String, String>> aList;

    private android.app.AlertDialog.Builder builder;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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

        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);
        builder = new android.app.AlertDialog.Builder(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Resources res = getResources();

        final TabHost tabs = (TabHost)rootView.findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("TOP VIDEOS", res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);
        //tabs.getTabWidget().getChildAt(tabs.getCurrentTab()).setBackgroundColor(Color.parseColor("#797D7F"));

        TextView tv = (TextView) tabs.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tv.setTextColor(Color.WHITE);


        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("TOP DESCARGAS", res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                int tab = tabs.getCurrentTab();
                for (int i = 0; i < tabs.getTabWidget().getChildCount(); i++) {
                    // When tab is not selected
                    //tabs.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#2b2b2b"));
                    TextView tv = (TextView) tabs.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(Color.parseColor("#FFFFFF"));
                }
                // When tab is selected
                //tabs.getTabWidget().getChildAt(tabs.getCurrentTab()).setBackgroundColor(Color.parseColor("#797D7F"));
                TextView tv = (TextView) tabs.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
                tv.setTextColor(Color.WHITE);
            }
        });

        tabs.setCurrentTab(0);

        mainLayout = (LinearLayout) rootView.findViewById(R.id.main_layout);

        busquedaRate();
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


    public void busquedaRate () {
        PLACES_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet,id&maxResults=50&key=AIzaSyA5SjEcYREnw0bFHeZPa21wKAr6sox5j3s";
        LOG_TAG = "VolleyPlacesRemoteDS";

        aList = new ArrayList<HashMap<String, String>>();

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
                            arr = Arrays.copyOf(arr, 50); // new size will be 10 elements
                            for (int i = 0; i < size; i++) {
                                JSONObject another_json_object = the_json_array.getJSONObject(i);
                                JSONObject result3 = new JSONObject(another_json_object.getString("snippet"));
                                tempList.add(result3.getString("title"));
                                JSONObject result4 = new JSONObject(another_json_object.getString("id"));
                                arr[i] = result4.getString("videoId");
                                JSONObject result5 = new JSONObject(result3.getString("thumbnails"));
                                JSONObject result6 = new JSONObject(result5.getString("default"));
                                //Toast.makeText(getContext(), "Hola: "+ result6.getString("url"), Toast.LENGTH_LONG).show();

                                //File tmpFile = urlImage(result6.getString("url"), i);

                                //ImageView image = (ImageView) getView().findViewById(R.id.iconList);

                                HashMap<String, String> hm = new HashMap<String, String>();
                                hm.put("name", result3.getString("title"));
                                hm.put("image", R.drawable.music_box + "");
                                //Toast.makeText(getContext(), Picasso.with(getContext()).load(result6.getString("url")).toString(), Toast.LENGTH_LONG).show();
                                aList.add(hm);
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

        pDialog.setMessage("Cargando Videos ...");
        showDialog();

        final ListView milista = (ListView)getView().findViewById(R.id.lista_tab1);

        final ImageView img=(ImageView)getView().findViewById(R.id.iconList);

        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, values){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                textView.setTextColor(Color.WHITE);

                return view;
            }
        };*/

        String[] from={"name","image"};//string array
        int[] to={R.id.nombre_fila_lista, R.id.iconList};//int array of views id's

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), aList, R.layout.fila_lista, from, to);
        milista.setAdapter(simpleAdapter);

        //milista.setAdapter(adapter);




        //mainLayout = (LinearLayout) getView().findViewById(R.id.main_layout);
        //mainLayout.setVisibility(View.GONE);

        milista.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int item = position;
                //String itemval = (String)milista.getItemAtPosition(position);
                YoutubeActivity.YOUTUBE_VIDEO_ID = arr[item];

                youtubeLink = "https://www.youtube.com/watch?v=";

                youtubeLink = youtubeLink + arr[item];

                customDialog = new Dialog(getContext(),R.style.Theme_AppCompat_DayNight_Dialog);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //customDialog.setCancelable(false);
                customDialog.setContentView(R.layout.dialog_option);

                TextView titulo = (TextView) customDialog.findViewById(R.id.titulo);
                titulo.setText("Opciones de Video");

                TextView contenido = (TextView) customDialog.findViewById(R.id.contenido);
                contenido.setText("¿Qué deseas hacer?");

                startActivity(new Intent(getActivity(), YoutubeActivity.class));

            }
        });
        hideDialog();

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public File urlImage(String urlString, int position) {
        InputStream iStream= null;
        URL url;
        File tmpFile = null;
        try {
            url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            File cacheDirectory = getActivity().getBaseContext().getCacheDir();
            tmpFile = new File(cacheDirectory.getPath() + "/wpta_" + position + ".jpg");
            FileOutputStream fOutStream = new FileOutputStream(tmpFile);
            Bitmap b = BitmapFactory.decodeStream(iStream);
            b.compress(Bitmap.CompressFormat.JPEG,10, fOutStream);
            Toast.makeText(getContext(), "Hola" + tmpFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
            fOutStream.flush();
            fOutStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return tmpFile;
    }
}
