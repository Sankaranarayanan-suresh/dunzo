package com.users.rider;

import com.application.Application;
import com.users.Users;
import com.utils.Utils;

import java.util.List;
import java.util.Scanner;


public class Rider extends Users {

    private final int serviceLocation;

    public int getServiceLocation() {
        return serviceLocation;
    }

    private boolean available = true;
    private double ratings;

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        if (this.ratings + ratings >= 5) {
            return;
        }
        this.ratings = ratings;
    }

    public Application.Job getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(Application.Job currentJob) {
        this.currentJob = currentJob;
    }

    private Application.Job currentJob;
    private final RiderInterface riderRequest;

    public Rider(String riderName, Long phoneNumber, String emailId, int serviceLocation, RiderInterface riderRequest) {
        super(phoneNumber.hashCode(), riderName, phoneNumber, emailId);
        this.serviceLocation = serviceLocation;
        this.riderRequest = riderRequest;
    }

    public void addJob(Application.Job job) {
        if (!this.available)
            return;
        this.currentJob = job;
        this.available = false;

    }

    @Override
    public String toString() {
        return "RiderId      :" + this.getId() + "\n" +
                "RiderName    :" + this.getName() + "\n" +
                "phoneNumber  :" + this.getPhoneNumber() + "\n" +
                "emailId      :" + this.getEmailId() + "\n" +
                "Ratings      :" + this.getRatings() + "\n";
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public void editProfile() {
        editProfile:
        while (true) {
            System.out.println("1.Change Name\n2.Change Mail-ID\n" +
                    "3.Change password\n4.Go back to main-menu");
            int editProfilePreference = Utils.getInteger();
            switch (editProfilePreference) {
                case 1:
                    System.out.print("Enter your new name: ");
                    String newName = new Scanner(System.in).nextLine();
                    riderRequest.changeName(this.getId(), newName);
                    System.out.println("Your name has been changed successfully:)");
                    break;
                case 2:
                    System.out.print("Enter you new Email-ID: ");
                    String newMailId = new Scanner(System.in).nextLine();
                    riderRequest.changeMailID(this.getId(), newMailId);
                    System.out.println("Your EmailId has been changed successfully:)");
                    break;
                case 3:
                    System.err.println("Changing your Password can affect your LOGIN credentials too!\n" +
                            "Do you want to continue? (y/n)");
                    String passwordConfirmation = new Scanner(System.in).nextLine();
                    if (passwordConfirmation.equalsIgnoreCase("y")) {
                        System.out.println("Enter your old password: ");
                        String oldPassword = new Scanner(System.in).nextLine();
                        System.out.println("Enter your New Password: ");
                        String newPassword = new Scanner(System.in).nextLine();
                        riderRequest.changePassword(oldPassword, newPassword, this.getPhoneNumber());
                    }
                case 4:
                    break editProfile;

            }
        }

    }

    @Override
    public void viewHistory() {
        List<Application.Job> history = riderRequest.fetchJobList(this.getPhoneNumber());
        if (history.size() == 0)
            System.out.println("No previous jobs");
        for (Application.Job jobs : history) {
            System.out.println(jobs + "\n");
        }
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    private void viewJob() {
        try {
            System.out.println(currentJob.toString());
            System.out.println("1.Change state of the object\n2.Exit");
            int riderDecision = Utils.getInteger();
            int objectState;
            if (riderDecision == 1) {
                if (currentJob.getObjectState().equalsIgnoreCase("Assigned")) {
                    System.out.println("1.Picked-Up\n2.Exit");
                    objectState = Utils.getInteger();
                    if (objectState == 1) {
                        riderRequest.changeJobState(currentJob, "Picked-Up");
                    }
                } else if (currentJob.getObjectState().equalsIgnoreCase("Picked-Up")) {
                    System.out.println("1.Delivered\n2.Not Delivered\n3.Exit");
                    objectState = Utils.getInteger();
                    if (objectState == 1) {
                        riderRequest.changeJobState(currentJob, "Delivered");
                    } else if (objectState == 2) {
                        System.out.println("State the exact reason: ");
                        String reason = new Scanner(System.in).nextLine();
                        riderRequest.changeJobState(currentJob, "Not delivered", reason);
                    } else if (objectState == 3) {
                        return;
                    }
                }

            }
        } catch (NullPointerException e) {
            System.out.println("Currently You have no jobs to complete.");
        }
    }

    private void cancelJob() {
        if (this.currentJob != null) {
            System.out.println("You have no job.");
            return;
        }
        System.out.println("Do you want to cancel the job");
        System.out.println("1.Cancel\n2.Exit");
        int riderPreference = Utils.getInteger();
        if (riderPreference == 1) {
            riderRequest.cancelJob(this.getPhoneNumber(), this.currentJob);
        }
    }


    @Override
    public void showMenu() {
        System.out.println("You have " + notifications.size() + " notifications");
        driverFunction:
        while (true) {
            System.out.println("\n1.Edit My profile\n2.View Job\n3.Cancel Job \n4.View Notification\n5.View my Profile\n" +
                    "6.View History\n7.Sign-Out");
            int customerPreference = Utils.getInteger();
            switch (customerPreference) {
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
