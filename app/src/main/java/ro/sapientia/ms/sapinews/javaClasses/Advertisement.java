package ro.sapientia.ms.sapinews.javaClasses;

import java.util.ArrayList;

public class Advertisement {

    private ArrayList<String> advertismentImage;
    private String advertismentTitle = "";
    private String advertismentShortDescription = "";
    private String advertismentLongDescription = "";
    private String advertismentProfilePicture;
    private String ownerPhoneNumber;
    private String location;
    private int viewedCounter = 0;
    private String isDeleted;
    private String key;

    public Advertisement(ArrayList<String> advertismentImage, String advertismentTitle, String advertismentShortDescription, String advertismentLongDescription, String advertismentProfilePicture, int viewedCounter, String ownerPhoneNumber, String location, String isDeleted, String key) {
        this.advertismentImage = advertismentImage;
        this.advertismentTitle = advertismentTitle;
        this.advertismentShortDescription = advertismentShortDescription;
        this.advertismentLongDescription = advertismentLongDescription;
        this.advertismentProfilePicture = advertismentProfilePicture;
        this.viewedCounter = viewedCounter;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.location = location;
        this.isDeleted = isDeleted;
        this.key = key;
    }

    public Advertisement() {
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ArrayList<String> getAdvertismentImage() {
        return advertismentImage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
        return "Advertisement{" +
                "advertismentImage=" + advertismentImage +
                ", advertismentTitle='" + advertismentTitle + '\'' +
                ", advertismentShortDescription='" + advertismentShortDescription + '\'' +
                ", advertismentLongDescription='" + advertismentLongDescription + '\'' +
                ", advertismentProfilePicture='" + advertismentProfilePicture + '\'' +
                ", viewedCounter=" + viewedCounter +
                '}';
    }
}
