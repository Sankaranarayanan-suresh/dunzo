package com.users;

public abstract class Users {
    private final int id;
    private String name;
    private long phoneNumber;
    private String emailId;

    protected Users(int id, String name, long phoneNumber, String emailId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }
    public void setName(String newName){
        this.name = newName;
    }
    public void setPhoneNumber(long newPhoneNumber){
        this.phoneNumber = newPhoneNumber;
    }
    public void setEmailId(String newMailId){
        this.emailId = newMailId;
    }
    public abstract void editProfile();
    public abstract void viewHistory();
    public abstract void viewProfile();
    public abstract void viewNotification();
    public abstract void driverFunction();
}
