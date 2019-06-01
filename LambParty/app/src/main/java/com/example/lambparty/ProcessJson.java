package com.example.lambparty;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ProcessJson {
    public String[][] jsonParse(String jsonStr) throws ParseException, JSONException {
        JSONParser jsonParser = new JSONParser();
        String[][] resultData = new String[3][6];

        Log.d(this.getClass().getName(), jsonStr);
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) jsonParser.parse(jsonStr);
        org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) jsonObject.get("items");

        for(int i=0;i<3;i++){
            org.json.simple.JSONObject tempObj = (org.json.simple.JSONObject) jsonArray.get(i);
            String title = tempObj.get("title").toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
            String description = tempObj.get("description").toString().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
                // <b> 제거 정규식
            resultData[i][0] = title;
            resultData[i][1] = tempObj.get("author").toString();
            resultData[i][2] = description;
            resultData[i][3] = tempObj.get("image").toString();
            resultData[i][4] = tempObj.get("link").toString();
            resultData[i][5] = tempObj.get("price").toString();
        }
        return resultData;
    }
}
