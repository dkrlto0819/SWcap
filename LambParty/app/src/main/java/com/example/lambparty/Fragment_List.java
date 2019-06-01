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

        Log.d(this.getClass().getName(), "This is json Data : " + jsonData);
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
//            String titleString = "list_bookTitle" + i;
//            String authorString = "list_bookAuthor" + i;
//            String contentString = "list_bookContent" + i;
//            String imageString = "list_bookImage" + i;

            imgUrl = resultData[3][i-1];

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
                new moveToBannerWeb().execute(i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return rootView;
    }

    class moveToBannerWeb extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... index) {
            return index[0];
        }

        @Override
        protected void onPostExecute(Integer result){
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

            Log.d(this.getClass().getName(), "This is title : "+ title);

            title.setText(resultData[0][result-1]);
            author.setText(resultData[1][result-1]);
            content.setText(resultData[2][result-1]);
            bookImage.setImageBitmap(bitmap);
        }
    }

}
