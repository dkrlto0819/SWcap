package com.example.lambparty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Fragment_Event extends Fragment {

    View rootView;
    Context context;

    ImageView aladin;
    ImageView yes24;
    ImageView kyobo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_event, container, false);
        context = getActivity();

        try {
            new moveToWeb().execute();
        } catch (Exception e){

        }


        return rootView;
    }

    private class moveToWeb extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            Log.d(this.getClass().getName(), "Hello!");
            aladin = (ImageView)getActivity().findViewById(R.id.event_alladin);
            yes24 = (ImageView)getActivity().findViewById(R.id.event_yes);
            kyobo = (ImageView)getActivity().findViewById(R.id.event_kyobo);

            aladin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.aladin.co.kr/events/wevent_main.aspx?start=we"));
                    startActivity(intent);
                }
            });

            yes24.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yes24.com/EventWorld/Main.aspx"));
                    startActivity(intent);
                }
            });

            kyobo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kyobobook.co.kr/event/eventMain.laf?orderClick=rvm"));
                    startActivity(intent);
                }
            });
        }
    }
}
