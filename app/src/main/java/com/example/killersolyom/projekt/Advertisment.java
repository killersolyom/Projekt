package com.example.killersolyom.projekt;

import android.net.Uri;

public class Advertisment {

    private String advertismentImage;
    private String advertismentTitle = "";
    private String advertismentDetails = "";
    private String advertismentProfilePicture;
    private int viewedCounter = 0;

    public Advertisment(String advertismentImage, String advertismentTitle, String advertismentDescription, String advertismentProfilePicture, int viewedCounter) {
        this.advertismentImage = advertismentImage;
        this.advertismentTitle = advertismentTitle;
        this.advertismentDetails = advertismentDescription;
        this.advertismentProfilePicture = advertismentProfilePicture;
        this.viewedCounter = viewedCounter;
    }

    public Advertisment() {
    }

    public String getAdvertismentImage() {
        return advertismentImage;
    }

    public void setAdvertismentImage(String advertismentImage) {
        this.advertismentImage = advertismentImage;
    }

    public String getAdvertismentTitle() {
        return advertismentTitle;
    }

    public void setAdvertismentTitle(String advertismentTitle) {
        this.advertismentTitle = advertismentTitle;
    }

    public String getAdvertismentDetails() {
        return advertismentDetails;
    }

    public void setAdvertismentDetails(String advertismentDescription) {
        this.advertismentDetails = advertismentDescription;
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
}
