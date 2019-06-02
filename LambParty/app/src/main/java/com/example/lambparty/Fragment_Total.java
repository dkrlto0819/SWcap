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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressLint("ValidFragment")
class Fragment_Total extends Fragment {
    View rootView;
    Context context;

    String[] data;
    String[][] list;
    int[] prices;

    TextView alladin;
    TextView alladin_price;
    TextView alladin_mileage;

    TextView yes24;
    TextView yes24_price;
    TextView yes24_mileage;

    TextView kyobo;
    TextView kyobo_price;
    TextView kyobo_mileage;


    TextView title;
    TextView author;
    TextView content;
    ImageView bookImage;

    Button all;
    Button price;
    Button mileage;

    Bitmap bitmap;
    String imgUrl;

    public Fragment_Total(String[] data) {
        this.data=data;
        list=new String[4][4];
        prices = new int[4];
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_total, container, false);
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
            new webCrawlingList().execute();
            new buttonClick().execute();

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
            title = (TextView)getActivity().findViewById(R.id.total_bookTitle);
            author = (TextView)getActivity().findViewById(R.id.total_bookAuthor);
            content = (TextView)getActivity().findViewById(R.id.total_bookContent);
            bookImage = (ImageView)getActivity().findViewById(R.id.total_bookImage);
            //price = (TextView)getActivity().findViewById(R.id.buybookPrice);

            title.setText(data[0]);
            author.setText(data[1]);
            content.setText(data[2]);
            bookImage.setImageBitmap(bitmap);
            //price.setText("정가 : "+ data[5]);
        }
    }

    class webCrawlingList extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Document document = Jsoup.connect(data[4]).get();
                Elements elements = document.select("div.npay_wrap a");
                int count=1;
                for(Element element : elements) {
                    if(count%3==1) {
                        int index = count/3;
                        list[index][0] = element.text();
                    }
                    if(count>=8) break;
                    count++;
                }
                elements = document.select("li.lowest span em");
                count=1;
                for(Element element : elements) {
                    list[count-1][1] = element.text();
                    prices[count-1]=Integer.parseInt(list[count-1][1].replace("원", ""));
                    if(count>=3) break;
                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String homeBookAuthor){
            alladin_price = (TextView)getActivity().findViewById(R.id.total_alladinPrice);
            alladin_mileage = (TextView)getActivity().findViewById(R.id.total_alladinMileage);

            list[2][2]="플래티넘 등급 : " +  Integer.toString(prices[2]/100*3) + " / "
                    + "골드등급 : " + Integer.toString(prices[2]/100*2) + " / "
                    + "실버등급 : " + Integer.toString(prices[2]/100);

            alladin_price.setText(list[2][1]);
            alladin_mileage.setText(list[2][2]);

            // *** alladin *** ///

            yes24_price = (TextView)getActivity().findViewById(R.id.total_yes24Price);
            yes24_mileage = (TextView)getActivity().findViewById(R.id.total_yes24Mileage);

            list[0][2]="플래티넘 등급 : " +  Integer.toString(prices[0]/100*3) + " / "
                    + "골드등급 : " + Integer.toString(prices[0]/100*2) + " / "
                    + "로얄등급 : " + Integer.toString(prices[0]/100);

            yes24_price.setText(list[0][1]);
            yes24_mileage.setText(list[0][2]);

            //**** yes24 **** ////

            kyobo_price = (TextView)getActivity().findViewById(R.id.total_kyoboPrice);
            kyobo_mileage = (TextView)getActivity().findViewById(R.id.total_kyoboMileage);

            list[1][2]="플래티넘 등급 : " +  Integer.toString(prices[1]/100*4) + " / "
                    + "골드등급 : " + Integer.toString(prices[1]/100*3) + " / "
                    + "실버등급 : " + Integer.toString(prices[1]/100*2);

            kyobo_price.setText(list[1][1]);
            kyobo_mileage.setText(list[1][2]);

            // *** kyobo *** ///

        }
    }

    class buttonClick extends AsyncTask<Void, Void, Void> {

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
            all = (Button)getActivity().findViewById(R.id.total_allButton);
            price = (Button)getActivity().findViewById(R.id.total_priceButton);
            mileage = (Button)getActivity().findViewById(R.id.total_mileageButton);

            all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alladin_price.setText(list[2][1]);
                    alladin_mileage.setText(list[2][2]);

                    yes24_price.setText(list[0][1]);
                    yes24_mileage.setText(list[0][2]);

                    kyobo_price.setText(list[1][1]);
                    kyobo_mileage.setText(list[1][2]);


                }
            });

            price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alladin_price.setText(list[2][1]);
                    alladin_mileage.setText("");

                    yes24_price.setText(list[0][1]);
                    yes24_mileage.setText("");

                    kyobo_price.setText(list[1][1]);
                    kyobo_mileage.setText("");

                }
            });

            mileage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alladin_price.setText("");
                    alladin_mileage.setText(list[2][2]);

                    yes24_price.setText("");
                    yes24_mileage.setText(list[0][2]);

                    kyobo_price.setText("");
                    kyobo_mileage.setText(list[1][2]);

                }
            });

        }
    }
}
