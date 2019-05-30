package com.example.lambparty;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ProcessJson {
    public void jsonParse(String jsonStr) throws ParseException, JSONException {
        JSONParser jsonParser = new JSONParser();

        Log.d(this.getClass().getName(), jsonStr);
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonParser.parse(jsonStr);
        org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) jsonObject.get("items");

//        for(int i=0;i<jsonArray.length();i++){
//            org.json.simple.JSONObject tempObj = (org.json.simple.JSONObject) jsonArray.get(i);
//            Log.d(this.getClass().getName(), "title : " + tempObj.get("title").toString());
//            Log.d(this.getClass().getName(), "price : "+ tempObj.get("price").toString());
//        }
    }
}
