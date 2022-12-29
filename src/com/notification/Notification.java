package com.notification;

public class Notification {
    private String notificationMessage;
    public String getNotificationMessage() {
        return notificationMessage;
    }

    public Notification setCustomerNotification(String notificationMessage) {
        this.notificationMessage = notificationMessage;
        return this;
    }
    public Notification setRiderNotification(String notificationMessage){
        this.notificationMessage = notificationMessage;
        return this;
    }
}
