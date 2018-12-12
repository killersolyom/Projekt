package ro.sapientia.ms.sapinews.javaClasses;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;

public class UriContainer {

    private ArrayList<Uri> uri = new ArrayList<>();
    private int currentIndex = 0;
    private int maxPicture = 4;

    public UriContainer() {
    }

    public void addUri(Uri image){
        if(uri.size() < maxPicture){
            uri.add(image);
        }
    }

    public Uri getNextImage(){
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

    public Uri getPreviousImage(){
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

    public ArrayList<Uri> getUri() {
        return uri;
    }

    public int getMaxPicture() {
        return maxPicture;
    }

    public void setMaxPicture(int maxPicture) {
        this.maxPicture = maxPicture;
    }

    public Uri getCurrentImage(){
        if(uri.size() == 1){
            return uri.get(0);
        }
        return uri.get(currentIndex);
    }
}
