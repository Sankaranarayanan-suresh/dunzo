package com.application;

import java.util.List;

public interface UserInterface {
    List<Application.Job> fetchJobList(Long phoneNumber);
    void changeMailID(long phoneNumber, String newMailId);
    void changeName(long phoneNumber, String newName);
    void changePassword(String oldPassword, String newPassword,long phoneNumber);
}
