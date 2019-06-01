package com.example.lambparty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.util.concurrent.ExecutionException;

@SuppressLint("ValidFragment")
public class Fragment_List extends Fragment {

    View rootView;
    Context context;

    String searchText;
    String jsonData;

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
        ProcessJson processJson = new ProcessJson();

        try {
            processJson.jsonParse(jsonData);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}
