package com.users.applicant;

public class Applicant {
    private final String name;
    private final long phoneNumber;
    private final String emailId;
    private final int serviceLocation;

    public String getName() {
        return name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }
    public int getServiceLocation() {
        return serviceLocation;
    }


    @Override
    public String toString() {
        return  "name        :" + name + '\n' +
                "phoneNumber :" + phoneNumber+"\n"+
                "emailId     :" + emailId ;
    }
    public Applicant(String name, long phoneNumber, int serviceLocation, String emailId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.serviceLocation = serviceLocation;
    }
}
