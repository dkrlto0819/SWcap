package com.example.bookapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Fragment_Banner extends Fragment {
    ImageView alladin;
    ImageView kyobo;
    ImageView yes24;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_app_bar_main, container, false);
        alladin = (ImageView)view.findViewById(R.id.alladin_banner);
        kyobo = (ImageView)view.findViewById(R.id.kyobo_banner);
        yes24 = (ImageView)view.findViewById(R.id.yes24_banner);

        alladin.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent siteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.aladin.co.kr/home/welcome.aspx"));
                startActivity(siteIntent);
            }
        });

        kyobo.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent siteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://digital.kyobobook.co.kr/digital/ebook/ebookMain.ink"));
                startActivity(siteIntent);
            }
        });

        alladin.setOnClickListener(new ImageView.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent siteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.yes24.com/main/default.aspx?ysmchn=ggl&ysmcpm=google-sponsor&ysmtac=ppc&ysmtrm=yes24&pid=123487&cosemkid=go14913756274066498&gclid=CjwKCAjwiN_mBRBBEiwA9N-e_idDHBLFtaa58XBCO3Eoy_7JT12apCvuNOx1QYDw6C9MgVXwyJj55RoCyjEQAvD_BwE"));
                startActivity(siteIntent);
            }
        });

        return view;
    }
}
