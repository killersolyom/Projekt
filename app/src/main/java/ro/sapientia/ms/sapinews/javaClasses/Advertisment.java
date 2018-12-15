package ro.sapientia.ms.sapinews.javaClasses;

import java.util.ArrayList;

public class Advertisment {

    private ArrayList<String> advertismentImage;
    private String advertismentTitle = "";
    private String advertismentShortDescription = "";
    private String advertismentLongDescription = "";
    private String advertismentProfilePicture;
    private String ownerPhoneNumber;
    private int viewedCounter = 0;

    public Advertisment(ArrayList<String> advertismentImage, String advertismentTitle, String advertismentShortDescription, String advertismentLongDescription, String advertismentProfilePicture, int viewedCounter, String ownerPhoneNumber) {
        this.advertismentImage = advertismentImage;
        this.advertismentTitle = advertismentTitle;
        this.advertismentShortDescription = advertismentShortDescription;
        this.advertismentLongDescription = advertismentLongDescription;
        this.advertismentProfilePicture = advertismentProfilePicture;
        this.viewedCounter = viewedCounter;
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public Advertisment() {
    }

    public ArrayList<String> getAdvertismentImage() {
        return advertismentImage;
    }

    public void setAdvertismentImage(ArrayList<String> advertismentImage) {
        this.advertismentImage = advertismentImage;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public String getAdvertismentTitle() {
        return advertismentTitle;
    }

    public void setAdvertismentTitle(String advertismentTitle) {
        this.advertismentTitle = advertismentTitle;
    }

    public String getAdvertismentShortDescription() {
        return advertismentShortDescription;
    }

    public void setAdvertismentShortDescription(String advertismentShortDescription) {
        this.advertismentShortDescription = advertismentShortDescription;
    }

    public String getAdvertismentLongDescription() {
        return advertismentLongDescription;
    }

    public void setAdvertismentLongDescription(String advertismentLongDescription) {
        this.advertismentLongDescription = advertismentLongDescription;
    }

    public String getAdvertismentProfilePicture() {
        return advertismentProfilePicture;
    }

    public void setAdvertismentProfilePicture(String advertismentProfilePicture) {
        this.advertismentProfilePicture = advertismentProfilePicture;
    }

    public int getViewedCounter() {
        return viewedCounter;
    }

    public void setViewedCounter(int viewedCounter) {
        this.viewedCounter = viewedCounter;
    }

    @Override
    public String toString() {
        return "Advertisment{" +
                "advertismentImage=" + advertismentImage +
                ", advertismentTitle='" + advertismentTitle + '\'' +
                ", advertismentShortDescription='" + advertismentShortDescription + '\'' +
                ", advertismentLongDescription='" + advertismentLongDescription + '\'' +
                ", advertismentProfilePicture='" + advertismentProfilePicture + '\'' +
                ", viewedCounter=" + viewedCounter +
                '}';
    }
}
