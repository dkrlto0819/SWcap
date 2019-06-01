package com.example.lambparty;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import org.jsoup.select.Elements;

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
    ImageView banner;

    Bitmap bitmap;

    String img;
    String title;
    String author;
    String content;
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

        new webCrawlingBookImage().execute();
        new webCrawlingBookTitle().execute();
        new webCrawlingBookAuthor().execute();
        new webCrawlingBookContent().execute();

        try {
            new moveToBannerWeb().execute();
        } catch (Exception e){

        }
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
                author = "저자 : " + element.text();
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

    class webCrawlingBookContent extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Document document = Jsoup.connect("https://book.naver.com/bestsell/bestseller_list.nhn").get();
                Elements element = document.select("dd#book_intro_0");
                content = element.text();
                content = content.replace("소개", "");
                content = content.substring(0, 90) + "...";
            } catch (IOException e) {
                e.printStackTrace();
            }

            return content;
        }

        @Override
        protected void onPostExecute(String homeBookContent){
            bookContent = (TextView)getActivity().findViewById(R.id.home_bookContent);
            bookContent.setText(homeBookContent);
        }
    }

    class moveToBannerWeb extends AsyncTask<Void, Void, Void>{

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
            banner = (ImageView)getActivity().findViewById(R.id.banner);

            banner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.aladin.co.kr/home/welcome.aspx"));
                    startActivity(intent);
                }
            });
        }
    }
}
