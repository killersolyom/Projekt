package com.example.killersolyom.projekt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyAdvertismentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);/*
        setContentView(R.layout.activity_my_advertisments);
        View view =  inflater(R.layout.fragment_home, container, false);
        RecyclerView recycle = view.findViewById(R.id.recycler_view);
        ArrayList<Advertisment> advertisments = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            advertisments.add(generateAdvertisment());
        }
        MyAdapter temp = new MyAdapter(this.getBaseContext(),advertisments);
        recycle.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));
        recycle.setAdapter(temp);*/
    }


    public Advertisment generateAdvertisment(){
        Advertisment advertisments = new Advertisment();
        advertisments.setAdvertismentTitle("Teszt");
        advertisments.setAdvertismentDetails("Ez egy teszt");
        advertisments.setViewedCounter(66);
        advertisments.setAdvertismentImage("https://coubsecure-s.akamaihd.net/get/b153/p/coub/simple/cw_timeline_pic/dc084aa3631/dffafd7f8fc57eeaf2c71/ios_large_1482287026_image.jpg");
        advertisments.setAdvertismentProfilePicture("https://46yuuj40q81w3ijifr45fvbe165m-wpengine.netdna-ssl.com/wp-content/uploads/2018/08/horseshoe-bend-600x370.jpg");
        return advertisments;
    }

}
