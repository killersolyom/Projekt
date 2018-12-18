package ro.sapientia.ms.sapinews.javaClasses;

import java.util.ArrayList;

public class ImageContainer {

    private ArrayList<String> Images = new ArrayList<>();
    private int currentIndex = 0;

    public ImageContainer() {
    }

    public String getCurrentImage(){
        if(Images.size() == 1){
            return Images.get(0);
        }
        return Images.get(currentIndex);
    }
    public String getNextImage(){
        if(Images.size() == 1){
            return Images.get(0);
        }
        if(currentIndex==Images.size()-1){
            currentIndex = 0;
            return Images.get(currentIndex);
        }else{
            currentIndex++;
            return Images.get(currentIndex);
        }
    }

    public String  getPreviousImage(){
        if(Images.size() == 1){
            return Images.get(0);
        }
        if(currentIndex==0){
            currentIndex = Images.size()-1;
            return Images.get(Images.size()-1);
        }else{
            currentIndex--;
            return Images.get(currentIndex);
        }
    }

    public boolean isEmpty(){
        if(Images.size() == 0){
            return true;
        }
        return false;
    }

    public void addImage(String image){
        Images.add(image);
    }

    public void overrideImage(ArrayList<String> Image){
        Images = Image;
    }

}
