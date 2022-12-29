package com.users.customer;

import com.notification.Notification;
import com.application.Application;
import com.users.Users;
import com.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends Users {
    private final CustomerInterface customerRequest;
    public Customer(String customerName, Long phoneNumber, String emailId,CustomerInterface customerRequest) {
        super(phoneNumber.hashCode(),customerName,phoneNumber,emailId);
        this.customerRequest = customerRequest;
    }
    private void trackOrder(){

    }
    @Override
    public void viewHistory(){
       List<Application.Job> history =  customerRequest.fetchJobList(this.getPhoneNumber());
        if (history.size() == 0)
            System.out.println("No previous jobs");
        for (Application.Job jobs : history) {
            System.out.println(jobs + "\n");
        }
    }
    private void bookService(){
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

//        System.out.println("Do you have any preferences");
        System.out.println("Confirm Booking?(y/n)");
        String confirmBooking =  new Scanner(System.in).nextLine();
        if (confirmBooking.equalsIgnoreCase("y")){
            customerRequest.bookAService(objectName,objectDescription,objectDimension,
                    pickUpPincode,dropPincode,this.getPhoneNumber());
        }
    }

    @Override
    public String toString() {
        return  "customerId   :" + this.getId() +"\n"+
                "customerName :" + this.getName() +"\n"+
                "phoneNumber  :" + this.getPhoneNumber() + "\n"+
                "emailId      :" + this.getEmailId() +"\n";
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
                    customerRequest.changeName(this.getPhoneNumber(),newName);
                    System.out.println("Your name has been changed successfully:)");
                    break;
                case 2:
                    System.err.println("Changing your Phone number can affect your LOGIN credentials too!\n" +
                            "Do you want to continue? (y/n)");
                    String confirmation = new Scanner(System.in).nextLine();
                    if (confirmation.equalsIgnoreCase("y")){
                        System.out.print("Enter your new Phone number: ");
                        long newPhoneNumber = Utils.getPhoneNumber();
                        customerRequest.changePhoneNumber(this.getPhoneNumber(),newPhoneNumber);
                        System.out.println("Your Phone number has been successfully changed:)");
                    }
                    break;
                case 3:
                    System.out.print("Enter you new Email-ID: ");
                    String newMailId = new Scanner(System.in).nextLine();
                    customerRequest.changeMailID(this.getPhoneNumber(),newMailId);
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
                        customerRequest.changePassword(oldPassword,newPassword,this.getPhoneNumber());
                    }
                case 5:
                    break editProfile;

            }
        }
    }
    public void driverFunction(){
        System.out.println("You have " +this.notifications.size()+ " notifications");
        driverFunction:while (true){
           System.out.println("\n1.Edit My profile\n2.Book a service\n3.Track My Order\n4.View Notifications\n5.View my Profile\n" +
                   "6.View History\n7.Sign-Out");
           int customerPreference = Utils.getInteger();
           switch (customerPreference){
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
               case 7:
                   break driverFunction;
           }
       }
    }
}
