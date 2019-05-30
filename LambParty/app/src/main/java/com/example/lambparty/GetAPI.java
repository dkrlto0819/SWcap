package com.example.lambparty;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

// **** JaeHee


public class GetAPI{

    public void getAPI(){
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask <Void, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String clientId = BuildConfig.LAMB_API_KEY;//애플리케이션 클라이언트 아이디값";
            String clientSecret = BuildConfig.LAMB_API_CLIENT;//애플리케이션 클라이언트 시크릿값";

            try {

                String text = URLEncoder.encode("토비의 스프링 3.1", "UTF-8");

                String apiURL = "https://openapi.naver.com/v1/search/book.json?query="+ text; // json 결과
                URL url = new URL(apiURL);

                Log.d(this.getClass().getName(), "URL : " + apiURL);

                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setRequestMethod("GET");

                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                int responseCode = con.getResponseCode();
                BufferedReader br;
                Log.d(this.getClass().getName(), "responseCode : " + responseCode);

                if(responseCode==200) { // 정상 호출

                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }

                String inputLine;

                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                Log.d(this.getClass().getName(), "Data : " + response.toString());

                ProcessJson processJson = new ProcessJson();
                processJson.jsonParse(response.toString());

            } catch (Exception e) {
                Log.d(this.getClass().getName(), "Fail");
                System.out.println(e);
            }
            return null;
        }
    }
}
