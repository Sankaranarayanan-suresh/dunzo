package com.application;

import com.users.customer.Customer;
import com.users.rider.Rider;
import com.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Application implements ApplicationInterface {
    Database db;
    public Application() {
        this.db = Database.getInstance();
    }
    public void open(String user) {
        while (true) {
            if (user.equalsIgnoreCase("customer")) {
                System.out.println("1.SignUp\n2.SignIn\n3.Go back to the previous menu");
                int preference = Utils.getInteger();
                Customer customer = null;
                if (preference == 1) {
                    //sign-up flow
                    System.out.println("Enter Your name: ");
                    String name = new Scanner(System.in).nextLine();

                    System.err.println("Remember the phone number that you enter now. \nYou will be using your phone number to login again.");
                    System.out.println("Enter Your phone number: ");
                    long phoneNumber = Utils.getPhoneNumber();
                    if (db.getUsersCredentials().containsKey(phoneNumber)) {
                        System.out.println("User already Exists!!!");
                        break;
                    }
                    System.out.println("Enter Your Email-ID: ");
                    String mail = new Scanner(System.in).nextLine();

                    while (true) {
                        System.out.println("Enter password for your account: ");
                        String password = new Scanner(System.in).nextLine();
                        System.out.println("Re-Enter your password: ");
                        String reenteredPassword = new Scanner(System.in).nextLine();
                        if (password.equals(reenteredPassword)) {
                            customer = new Customer(name, phoneNumber, mail, this);
                            db.addUser(customer, password);
                            System.out.println("\n\nSignUp Successful!!");
                            break;
                        } else {
                            System.err.println("Password Mismatch");
                        }
                    }

                } else if (preference == 2) {
                    while (true) {
                        System.out.println("Enter Your phone number: ");
                        long phoneNumber = Utils.getPhoneNumber();
                        System.out.println("Enter the password: ");
                        String password = new Scanner(System.in).nextLine();
                        if (checkCredentials(phoneNumber, password)) {
                            System.out.println("\n\nLogin Successfully Completed!!");
                            customer = (Customer) db.getUsers().get(phoneNumber);
                            break;
                        } else {
                            System.err.println("Incorrect credentials!!");
                        }
                    }
                } else if (preference == 3) {
                    break;
                }
                assert customer != null;
                customer.driverFunction();
                break;
            } else if (user.equalsIgnoreCase("rider")) {
                System.out.println("1.SignUp\n2.SignIn");
                int preference = Utils.getInteger();
                Rider rider = null;
                if (preference == 1) {
                    System.out.println("Enter Your name: ");
                    String name = new Scanner(System.in).nextLine();
                    System.err.println("Remember the phone number that you enter now. \nYou will be using your phone number to login again.");
                    System.out.println("Enter Your phone number: ");
                    long phoneNumber = Utils.getPhoneNumber();
                    System.out.println("Enter Your Email-ID: ");
                    String mail = new Scanner(System.in).nextLine();
                    rider = new Rider(name, phoneNumber, mail, this);
                } else if (preference == 2) {
                    while (true) {
                        System.out.println("Enter Your phone number: ");
                        long phoneNumber = Utils.getPhoneNumber();
                        System.out.println("Enter the password: ");
                        String password = new Scanner(System.in).nextLine();
                        if (checkCredentials(phoneNumber, password)) {
                            System.out.println("Login Successfully Completed!!");
                            rider = (Rider) db.getUsers().get(phoneNumber);
                            break;
                        } else {
                            System.err.println("Incorrect credentials!!");
                        }
                    }
                }
                assert rider != null;
                rider.driverFunction();
                break;
            }
        }

    }

    public void close() {
        System.exit(1);
    }

    @Override
    public List<Job> fetchJobList(int id) {
        List<Job> jobs = db.getJobDatabase();
        List<Job> userHistoryOfJobs = new ArrayList<>();
        for (Job job:jobs) {
            if (job.customerId == id || job.riderId == id){
                userHistoryOfJobs.add(job);
            }
        }
        return userHistoryOfJobs;
    }

    @Override
    public void addRiderToDatabase(Rider newRider, String password) {

    }

    private boolean checkCredentials(long phoneNumber, String password) {
        String actualPassword = db.getUsersCredentials().get(phoneNumber);
        return actualPassword.equals(password);
    }

    @Override
    public void changeMailID(long phoneNumber, String newMailId) {
        db.getUsers().get(phoneNumber).setEmailId(newMailId);
    }

    @Override
    public void changeName(long phoneNumber, String newName) {
        db.getUsers().get(phoneNumber).setName(newName);
    }

    @Override
    public void changePhoneNumber(long oldPhoneNumber, long newPhoneNumber) {
        db.getUsers().get(oldPhoneNumber).setPhoneNumber(newPhoneNumber);
        db.getUsers().put(newPhoneNumber, db.getUsers().remove(oldPhoneNumber));
        db.getUsersCredentials().put(newPhoneNumber, db.getUsersCredentials().remove(oldPhoneNumber));
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, long phoneNumber) {
        if (checkCredentials(phoneNumber, oldPassword)) {
            db.getUsersCredentials().replace(phoneNumber, newPassword);
            System.out.println("Password successfully changed:)");
        } else {
            System.err.println("Password Mismatch!!!");
        }
    }

    @Override
    public void bookAService(String objectName, String objectDescription, int objectDimension,
                             int pickUpPincode, int dropPincode, int customerId) {
        List<Rider> riderList = db.getRiders();
        Random random = new Random();
        int riderIndex = random.nextInt(riderList.size());
        Rider assignedRider = riderList.get(riderIndex);
        Job job = new Job(objectName, objectDescription, objectDimension,
                customerId, pickUpPincode, dropPincode, assignedRider.getId());
        assignedRider.addJob(job);
        db.addJobs(job);
        db.addToCurrentlyRunningJobs(job,assignedRider.getId());

    }

    @Override
    public void setJobState() {

    }

    @Override
    public void cancelJob() {

    }

    public static class Job {
        private final int jobId;
        private final String objectName;
        private final String objectDescription;
        private final int objectDimension;
        private final String objectState;
        private final int customerId;
        private final int riderId;
        private final int pickupLocation;
        private final int dropLocation;

        public int getJobId() {
            return jobId;
        }

        public String getObjectName() {
            return objectName;
        }

        public String getObjectDescription() {
            return objectDescription;
        }

        public int getObjectDimension() {
            return objectDimension;
        }

        public String getObjectState() {
            return objectState;
        }

        public int getCustomerId() {
            return customerId;
        }

        public int getPickupLocation() {
            return pickupLocation;
        }

        public int getDropLocation() {
            return dropLocation;
        }

        public int getRiderId() {
            return riderId;
        }

        private Job(String objectName, String objectDescription, int objectDimension,
                    int customerId, int pickupLocation, int dropLocation, int riderId) {
            this.jobId = this.hashCode();
            this.objectName = objectName;
            this.objectDescription = objectDescription;
            this.objectDimension = objectDimension;
            this.objectState = "Assigned";
            this.customerId = customerId;
            this.riderId = riderId;
            this.pickupLocation = pickupLocation;
            this.dropLocation = dropLocation;
        }

    }
}
