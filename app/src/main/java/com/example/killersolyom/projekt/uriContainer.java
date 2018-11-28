package com.example.killersolyom.projekt;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class uriContainer {

    private ArrayList<Bitmap> uri = new ArrayList<>();
    private int currentIndex = 0;

    public uriContainer() {
    }

    public void addUri(Bitmap bitmap){
        uri.add(bitmap);
    }

    public Bitmap getNextImage(){
        if(uri.size() == 1){
            return uri.get(0);
        }
        if(currentIndex==uri.size()-1){
            currentIndex = 0;
            return uri.get(currentIndex);
        }else{
            currentIndex++;
            return uri.get(currentIndex);
        }
    }

    public Bitmap getPreviousImage(){
        if(uri.size() == 1){
            return uri.get(0);
        }
        if(currentIndex==0){
            currentIndex = uri.size()-1;
            return uri.get(uri.size()-1);
        }else{
            currentIndex--;
            return uri.get(currentIndex);
        }
    }

    public boolean isEmpty(){
        if(uri.size() == 0){
            return true;
        }
        return false;
    }

    public Bitmap getCurrentImage(){
        if(uri.size() == 1){
            return uri.get(0);
        }
        return uri.get(currentIndex);
    }
}
