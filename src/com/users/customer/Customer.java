package com.users.customer;

import com.application.Application;
import com.users.Users;
import com.utils.Utils;

import java.util.List;
import java.util.Scanner;

public class Customer extends Users {
    private final CustomerInterface customerRequest;

    public Customer(String customerName, Long phoneNumber, String emailId, CustomerInterface customerRequest) {
        super(phoneNumber.hashCode(), customerName, phoneNumber, emailId);
        this.customerRequest = customerRequest;
    }

    private void trackOrder() {
        List<Application.Job> myCurrentJob = customerRequest.getJobDetails(this.getPhoneNumber());
        if (myCurrentJob.size() > 0) {
            for (Application.Job job : myCurrentJob) {
                System.out.println(job.toString() + "\n");
            }
        } else {
            System.out.println("You don't have any jobs currently running.");
        }
    }

    @Override
    public void viewHistory() {
        List<Application.Job> history = customerRequest.fetchJobList(this.getPhoneNumber());
        if (history.size() == 0)
            System.out.println("No previous jobs");
        for (Application.Job jobs : history) {
            System.out.println(jobs + "\n");
        }
    }

    private void bookService() {
        System.out.print("Enter Object Name: ");
        String objectName = new Scanner(System.in).nextLine();

        System.out.print("Enter Object description(in max of 50 words): ");
        String objectDescription = new Scanner(System.in).nextLine();

        System.out.print("Enter Object Dimension(area): ");
        int objectDimension = Utils.getInteger();

        System.out.print("Enter pick-up location address: ");
        String pickUpAddress = new Scanner(System.in).nextLine();

        System.out.print("Enter pick-up location pincode: ");
        int pickUpPincode = Utils.getInteger();

        System.out.print("Enter drop location address: ");
        String dropAddress = new Scanner(System.in).nextLine();

        System.out.print("Enter Drop location pincode: ");
        int dropPincode = Utils.getInteger();

        System.out.println("Do you need any specific rating for the rider?(y/n)");
        double rating = 0;
        double actualPrice;
        String ratingPreference = new Scanner(System.in).nextLine();
        if (ratingPreference.equalsIgnoreCase("y")) {
            System.out.println("Enter minimum rating: ");
            rating = Utils.getRatings();
            actualPrice = customerRequest.estimatedPrice(pickUpPincode,dropPincode,objectDimension,(int) rating);
        }
        else {
            actualPrice = customerRequest.estimatedPrice(pickUpPincode, dropPincode, objectDimension, 0);
        }
        System.out.println("Confirm Booking?(y/n)");
        String confirmBooking = new Scanner(System.in).nextLine();
        if (confirmBooking.equalsIgnoreCase("y") && ratingPreference.equalsIgnoreCase("y")) {
            if (customerRequest.bookAService(objectName, objectDescription, objectDimension,
                    pickUpPincode, dropPincode, this.getPhoneNumber(), rating,pickUpAddress,dropAddress)){
                System.out.println("Initializing Payment process...");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                payment(actualPrice);
            }
        } else if (confirmBooking.equalsIgnoreCase("y")) {
            if ( customerRequest.bookAService(objectName, objectDescription, objectDimension,
                    pickUpPincode, dropPincode, this.getPhoneNumber(),pickUpAddress,dropAddress)){
                System.out.println("Initializing Payment process...");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                payment(actualPrice);
            }
        }
    }
    public double requestRating(){
        return Utils.getRatings();
    }
    private void payment(double actualAmount) {
        System.out.print("Enter the amount: Rs.");
        double amount = Utils.getAmount();
        if (amount != actualAmount) {
            System.err.println("Incorrect amount!!!!");
            payment(actualAmount);
        }
        System.out.println("Payment Successful:)");
    }



    @Override
    public String toString() {
        return "customerId   :" + this.getId() + "\n" +
                "customerName :" + this.getName() + "\n" +
                "phoneNumber  :" + this.getPhoneNumber() + "\n" +
                "emailId      :" + this.getEmailId() + "\n";
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
                    customerRequest.changeName(this.getPhoneNumber(), newName);
                    System.out.println("Your name has been changed successfully:)");
                    break;
                case 2:
                    System.out.print("Enter you new Email-ID: ");
                    String newMailId = new Scanner(System.in).nextLine();
                    customerRequest.changeMailID(this.getPhoneNumber(), newMailId);
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
                        customerRequest.changePassword(oldPassword, newPassword, this.getPhoneNumber());
                    }
                case 4:
                    break editProfile;

            }
        }
    }

    public void showMenu() {
        System.out.println("You have " + this.notifications.size() + " notifications");
        driverFunction:
        while (true) {
            System.out.println("\n1.Edit My profile\n2.Book a service\n3.Track My Job\n4.View Notifications\n5.View my Profile\n" +
                    "6.View History\n7.Sign-Out");
            int customerPreference = Utils.getInteger();
            switch (customerPreference) {
                case 1:
                    editProfile();
                    break;
                case 2:
                    bookService();
                    break;
                case 3:
                    trackOrder();
                    break;
                case 4:
                    viewNotification();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    viewHistory();
                    break;
                case 7:
                    break driverFunction;
            }
        }
    }
}
