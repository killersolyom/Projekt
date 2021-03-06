package ro.sapientia.ms.sapinews.javaClasses;

import java.util.ArrayList;

public class User {

    private static User single_instance = null;

    private String ID;
    private String firstName;
    private String lastName;
    private String phoneNumb;
    private String emailAddress;
    private String address;
    private String imageUrl;
    private ArrayList<String> adKeys = new ArrayList<>();

    private User(){

    }


    public void setAdKeys(ArrayList<String> adKeys) {
        this.adKeys = adKeys;
    }

    public static User getInstance(){
        if(single_instance == null){
            single_instance = new User();
        }
        return single_instance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumb(String phoneNumb) {
        this.phoneNumb = phoneNumb;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumb() {
        return phoneNumb;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setAdvKeysToArrayList (String key){
        adKeys.add(key);
    }

    public ArrayList<String> getAdKeys() {
        return adKeys;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID='" + ID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumb='" + phoneNumb + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", address='" + address + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", adKeys=" + adKeys +
                '}';
    }
}
