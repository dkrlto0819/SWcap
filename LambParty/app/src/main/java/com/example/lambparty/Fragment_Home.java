package com.example.lambparty;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Fragment_Home extends Fragment {

    View rootView;
    Context context;

    ImageView homeImageView;
    TextView bookTitle;
    TextView bookAuthor;
    TextView bookContent;

    Bitmap bitmap;

    String img;
    String title;
    String author;
    URL imgUrl;

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
//        homeImageView = (ImageView)getActivity().findViewById(R.id.home_bookImage);
        new webCrawlingBookImage().execute();
        new webCrawlingBookTitle().execute();
        new webCrawlingBookAuthor().execute();
        return rootView;
    }

    class webCrawlingBookImage extends AsyncTask<Void, Void, Bitmap>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                Document document = Jsoup.connect("https://book.naver.com/bestsell/bestseller_list.nhn").get();
                Element element = document.select("div.thumb_type a img").first();
                img = element.attr("src");
                imgUrl = new URL(img);

                element = document.select("div.thumb a img").first();
                title = element.attr("alt");
                Log.d(this.getClass().getName(), "element : " + title);

                HttpURLConnection connection = (HttpURLConnection)imgUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bookImage){
            Log.d(this.getClass().getName(), "bookImage : " + bookImage);
            homeImageView = (ImageView)getActivity().findViewById(R.id.home_bookImage);
            homeImageView.setImageBitmap(bookImage);
        }
    }

    class webCrawlingBookTitle extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Document document = Jsoup.connect("https://book.naver.com/bestsell/bestseller_list.nhn").get();
                Element element = document.select("div.thumb a img").first();
                title = element.attr("alt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return title;
        }

        @Override
        protected void onPostExecute(String homeBookTitle){
            bookTitle = (TextView)getActivity().findViewById(R.id.home_bookTitle);
            bookTitle.setText(homeBookTitle);
        }
    }

    class webCrawlingBookAuthor extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Document document = Jsoup.connect("https://book.naver.com/bestsell/bestseller_list.nhn").get();
                Element element = document.select("dd.txt_block a").first();
                author = element.text();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return author;
        }

        @Override
        protected void onPostExecute(String homeBookAuthor){
            bookAuthor = (TextView)getActivity().findViewById(R.id.home_bookAuthor);
            bookAuthor.setText(homeBookAuthor);
        }
    }

}
