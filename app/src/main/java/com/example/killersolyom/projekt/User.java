package com.example.killersolyom.projekt;

public class User {

    public String firstName;
    public String lastName;
    public String phoneNumb;


    public User(){

    }

    public User(String firstName, String lastName, String phoneNumb){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumb = phoneNumb;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getPhoneNumb() {
        return phoneNumb;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumb='" + phoneNumb + '\'' +
                '}';
    }
}
