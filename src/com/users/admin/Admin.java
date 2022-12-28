package com.users.admin;

import com.users.Users;
import com.users.rider.RiderInterface;

public class Admin extends Users {
    private AdminInterface adminRequest;
    public Admin(String name, Long phoneNumber, String emailId, AdminInterface adminRequest) {

        super(phoneNumber.hashCode(),name,phoneNumber,emailId);
        this.adminRequest = adminRequest;
    }

    @Override
    public void setName(String newName) {

    }

    @Override
    public void setPhoneNumber(long newPhoneNumber) {

    }

    @Override
    public void setEmailId(String newMailId) {

    }

    @Override
    public void editProfile() {

    }

    @Override
    public void viewHistory() {

    }

    @Override
    public void viewProfile() {

    }

    @Override
    public void viewNotification() {

    }

    @Override
    public void driverFunction() {

    }
}
