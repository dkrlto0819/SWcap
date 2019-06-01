package com.example.lambparty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class Fragment_Buy extends Fragment {
    String link;

    View rootView;
    Context context;
    @SuppressLint("ValidFragment")
    Fragment_Buy(String link){
        this.link = link;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_buy, container, false);
        context = getActivity();

        return rootView;
    }
}
