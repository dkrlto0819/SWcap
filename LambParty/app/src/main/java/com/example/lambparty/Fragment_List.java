package com.example.lambparty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

@SuppressLint("ValidFragment")
public class Fragment_List extends Fragment {

    View rootView;
    Context context;

    String searchText;
    String jsonData;

    TextView title;
    TextView author;
    TextView content;
    ImageView bookImage;

    Bitmap bitmap;
    String imgUrl;

    int titleID;
    int authorID;
    int contentID;
    int imageID;

    String resultData[][];

    @SuppressLint("ValidFragment")
    Fragment_List(String searchText){
        this.searchText = searchText;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        context = getActivity();

        GetAPI getAPI = new GetAPI();
        try {
            jsonData = getAPI.getAPI(searchText);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resultData = new String[10][10];
        ProcessJson processJson = new ProcessJson();
        try {
            resultData = processJson.jsonParse(jsonData);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=1;i<=3;i++){
            imgUrl = resultData[i-1][3];

            Thread mThread = new Thread(){
                @Override
                public void run(){
                    try{
                        URL url = new URL(imgUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();

                        InputStream is = connection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            };

            mThread.start();
            try{
                mThread.join();
                new makeList().execute(i);
                new selectBook().execute(i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return rootView;
    }

    class makeList extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... index) {
            return index[0];
        }

        @Override
        protected void onPostExecute(Integer result){  /// textView 구성
            String titleString = "list_bookTitle" + result;
            String authorString = "list_bookAuthor" + result;
            String contentString = "list_bookContent" + result;
            String imageString = "list_bookImage" + result;

            titleID = getResources().getIdentifier(titleString, "id", getActivity().getPackageName());
            authorID = getResources().getIdentifier(authorString, "id", getActivity().getPackageName());
            contentID = getResources().getIdentifier(contentString, "id", getActivity().getPackageName());
            imageID = getResources().getIdentifier(imageString, "id", getActivity().getPackageName());

            title = (TextView)getActivity().findViewById(titleID);
            author = (TextView)getActivity().findViewById(authorID);
            content = (TextView)getActivity().findViewById(contentID);
            bookImage = (ImageView)getActivity().findViewById(imageID);

            title.setText(resultData[result-1][0]);
            author.setText(resultData[result-1][1]);
            content.setText(resultData[result-1][2]);
            bookImage.setImageBitmap(bitmap);
        }
    }

    class selectBook extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... index) {
            return index[0];
        }

        @Override
        protected void onPostExecute(final Integer result){
            String titleString = "list_bookTitle" + result;
            String authorString = "list_bookAuthor" + result;
            String contentString = "list_bookContent" + result;
            String imageString = "list_bookImage" + result;

            titleID = getResources().getIdentifier(titleString, "id", getActivity().getPackageName());
            authorID = getResources().getIdentifier(authorString, "id", getActivity().getPackageName());
            contentID = getResources().getIdentifier(contentString, "id", getActivity().getPackageName());
            imageID = getResources().getIdentifier(imageString, "id", getActivity().getPackageName());

            title = (TextView)getActivity().findViewById(titleID);
            author = (TextView)getActivity().findViewById(authorID);
            content = (TextView)getActivity().findViewById(contentID);
            bookImage = (ImageView)getActivity().findViewById(imageID);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new Fragment_Buy(resultData[result-1]))
                            .commit();
                }
            });

            author.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new Fragment_Buy(resultData[result-1]))
                            .commit();
                }
            });

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new Fragment_Buy(resultData[result-1]))
                            .commit();
                }
            });

            bookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new Fragment_Buy(resultData[result-1]))
                            .commit();
                }
            });

        }
    }
}
