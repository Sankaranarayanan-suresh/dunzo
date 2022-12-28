package com.users.rider;

import com.Notification;
import com.application.Application;
import com.users.Users;
import com.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Rider extends Users {

    private boolean available;
    private Application.Job currentJob;
    private final RiderInterface riderRequest;
    private final List<Notification> notifications = new ArrayList<>();

    public Rider(String riderName, Long phoneNumber, String emailId, RiderInterface riderRequest) {
        super(phoneNumber.hashCode(),riderName,phoneNumber,emailId);
        this.riderRequest = riderRequest;
    }
    public void addJob(Application.Job job){
        if (!this.available)
            return;
        this.currentJob = job;
        this.available = false;

    }
    public void addNotification(Notification notification){
        notifications.add(notification);
    }
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void editProfile() {
        editProfile: while (true){
            System.out.println("1.Change Name\n2.Change Phone number\n3.Change Mail-ID\n" +
                    "4.Change password\n5.Go back to main-menu");
            int editProfilePreference = Utils.getInteger();
            switch (editProfilePreference){
                case 1:
                    System.out.print("Enter your new name: ");
                    String newName = new Scanner(System.in).nextLine();
                    riderRequest.changeName(this.getId(),newName);
                    System.out.println("Your name has been changed successfully:)");
                    break;
                case 2:
                    System.err.println("Changing your Phone number can affect your LOGIN credentials too!\n" +
                            "Do you want to continue? (y/n)");
                    String confirmation = new Scanner(System.in).nextLine();
                    if (confirmation.equalsIgnoreCase("y")){
                        System.out.print("Enter your new Phone number: ");
                        long newPhoneNumber = Utils.getPhoneNumber();
                        riderRequest.changePhoneNumber(this.getPhoneNumber(),newPhoneNumber);
                        System.out.println("Your Phone number has been successfully changed:)");
                    }
                    break;
                case 3:
                    System.out.print("Enter you new Email-ID: ");
                    String newMailId = new Scanner(System.in).nextLine();
                    riderRequest.changeMailID(this.getId(),newMailId);
                    System.out.println("Your EmailId has been changed successfully:)");
                    break;
                case 4:
                    System.err.println("Changing your Password can affect your LOGIN credentials too!\n" +
                            "Do you want to continue? (y/n)");
                    String passwordConfirmation = new Scanner(System.in).nextLine();
                    if (passwordConfirmation.equalsIgnoreCase("y")){
                        System.out.println("Enter your old password: ");
                        String oldPassword = new Scanner(System.in).nextLine();
                        System.out.println("Enter your New Password: ");
                        String newPassword = new Scanner(System.in).nextLine();
                        riderRequest.changePassword(oldPassword,newPassword,this.getPhoneNumber());
                    }
                case 5:
                    break editProfile;

            }
        }

    }

    @Override
    public void viewHistory() {
        List<Application.Job> history =  riderRequest.fetchJobList(this.getId());
        if (history.size() == 0)
            System.out.println("No previous jobs");
        for (Application.Job jobs : history) {
            System.out.println(jobs + "\n");
        }
    }

    @Override
    public void viewProfile() {
        System.out.println(this);
    }

    @Override
    public void viewNotification() {
        for (Notification notification:notifications) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(notification);
            notifications.remove(notification);
        }
    }

    private void viewJob() {
        System.out.println(currentJob.toString());
    }
    private void cancelJob() {
        riderRequest.cancelJob();
    }


    @Override
    public void driverFunction() {
        System.out.println("You have " +notifications.size()+ " notifications");
        driverFunction:while (true){
            System.out.println("\n1.Edit My profile\n2.View Job\n3.Cancel Job \n4.View Notification\n5.View my Profile\n" +
                    "6.View History\n7.Sign-Out");
            int customerPreference = Utils.getInteger();
            switch (customerPreference){
                case 1:
                    editProfile();
                    break;
                case 2:
                    viewJob();
                    break;
                case 3:
                    cancelJob();
                    break;
                case 4:
                    viewNotification();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    viewHistory();
                case 7:
                    break driverFunction;
            }
        }
    }
}
