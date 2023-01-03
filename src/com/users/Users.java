package com.users;

import com.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public abstract class Users {
    private final int id;
    private String name;
    private long phoneNumber;
    private String emailId;
    protected final List<Notification> notifications = new ArrayList<>();

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

    public void setName(String newName) {
        this.name = newName;
    }

    public void setPhoneNumber(long newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }

    public void setEmailId(String newMailId) {
        this.emailId = newMailId;
    }

    public void pushNotification(Notification notificationMessage) {
        notifications.add(notificationMessage);
    }

    public void viewNotification() {
        if (notifications.size()>0){
            for (Notification notification : notifications) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(notification.getNotificationMessage());
            }
            notifications.clear();
        }else {
            System.out.println("You have no new notifications!!\n");
        }
    }
    public void viewProfile() {
        System.out.println(this);
    }

    public abstract void editProfile();
    public abstract void viewHistory();
    public abstract void showMenu();
}
