package com.example.killersolyom.projekt;

public class User {

    private Integer ID;
    private String firstName;
    private String lastName;
    private String phoneNumb;
    private String emailAddress;
    public String address;

    public User(){

    }

    public User(String firstName, String lastName, String phoneNumb, String emailAddress, String address){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumb = phoneNumb;
        this.emailAddress = emailAddress;
        this.address = address;
        this.ID=0;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
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

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumb='" + phoneNumb + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
