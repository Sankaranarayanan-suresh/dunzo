package com.application;

import com.application.Application;

import java.util.List;

public interface UserInterface {
    List<Application.Job> fetchJobList(int id);
    void changeMailID(long phoneNumber, String newMailId);
    void changeName(long phoneNumber, String newName);
    void changePhoneNumber(long oldPhoneNumber,long newPhoneNumber);
    void changePassword(String oldPassword, String newPassword,long phoneNumber);
}
