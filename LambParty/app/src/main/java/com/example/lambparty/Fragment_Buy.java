package com.example.lambparty;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressLint("ValidFragment")
public class Fragment_Buy extends Fragment {
    String[] data;

    View rootView;
    Context context;

    TextView title;
    TextView author;
    TextView content;
    ImageView bookImage;
    TextView price;
    Button button;

    Bitmap bitmap;
    String imgUrl;


    @SuppressLint("ValidFragment")
    Fragment_Buy(String[] data){
        this.data = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_buy, container, false);
        context = getActivity();

        imgUrl = data[3];

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
            new makeList().execute();
            new goToBuyPage().execute();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    class makeList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result){  /// textView 구성
            title = (TextView)getActivity().findViewById(R.id.buybookTitle);
            author = (TextView)getActivity().findViewById(R.id.buybookWriter);
            content = (TextView)getActivity().findViewById(R.id.buybookcontent);
            bookImage = (ImageView)getActivity().findViewById(R.id.buybookImage);
            price = (TextView)getActivity().findViewById(R.id.buybookPrice);

            title.setText(data[0]);
            author.setText(data[1]);
            content.setText(data[2]);
            bookImage.setImageBitmap(bitmap);
            price.setText("정가 : "+ data[5]);
        }
    }

    class goToBuyPage extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            button = (Button)getActivity().findViewById(R.id.buyButton);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new Fragment_Total(data))
                            .commit();
                }
            });

        }
    }
}
