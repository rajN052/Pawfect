package com.example.aaochatein;

public class Users
{
    String name;
    String phone_no;
    String email;
    String photo;
    String userID;
    String dogbreed;
    String gender;
    String prevmenscycle;
    String Dogname;
    String DogAge;




    public Users(String name, String phone_no, String email, String photo, String userID) {
        this.name = name;
        this.phone_no = phone_no;
        this.email = email;
        this.photo = photo;
        this.userID = userID;
    }

    public Users(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDogbreed() {
        return dogbreed;
    }

    public void setDogbreed(String dogbreed) {
        this.dogbreed = dogbreed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getPrevmenscycle() {
        return prevmenscycle;
    }

    public void setPrevmenscycle(String prevmenscycle) {
        this.prevmenscycle = prevmenscycle;
    }

    public String getDogname() {
        return Dogname;
    }

    public void setDogname(String dogname) {
        Dogname = dogname;
    }

    public String getDogAge() {
        return DogAge;
    }

    public void setDogAge(String dogAge) {
        DogAge = dogAge;
    }







}
