package com.example.lambparty;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Documented;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Fragment_Home extends Fragment {

    View rootView;
    Context context;

    ImageView homeImageView;
    TextView bookTitle;
    TextView bookAuthor;
    TextView bookContent;

    Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        context=getActivity();

        new webCrawling().execute();
        return rootView;
    }

    class webCrawling extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document document = Jsoup.connect("https://book.naver.com/bestsell/bestseller_list.nhn").get();
                Element element = document.select("div.thumb_type a img").first();
                String img = element.attr("src");

                URL imgUrl = new URL(img);

                HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                bitmap= BitmapFactory.decodeStream(is);

                homeImageView = (ImageView)getActivity().findViewById(R.id.home_bookImage);
                homeImageView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }

            Thread mThread = new Thread(){
                @Override
                public void run(){

                }
            }

            return null;
        }
    }
}
