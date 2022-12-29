package com.application;

import com.notification.Notification;
import com.users.Applicant;
import com.users.admin.Admin;
import com.users.admin.AdminInterface;
import com.users.customer.Customer;
import com.users.rider.Rider;
import com.utils.Utils;
import jdk.nashorn.internal.scripts.JO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Application implements ApplicationInterface {
    Database db;
    Admin admin;
    public Application() {
        this.db = Database.getInstance();
        admin = new Admin("sankar", 1234567890L,"qwerty", this);
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
                else {
                    System.err.println("Enter correct Option!!!");
                    continue;
                }
                assert customer != null;
                customer.driverFunction();
                break;
            } else if (user.equalsIgnoreCase("rider")) {
                System.out.println("1.Apply for rider\n2.SignIn");
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
                    Applicant applicant = new Applicant(name, phoneNumber, mail);
                    admin.addToApprovalList(applicant);
                } else if (preference == 2) {
                    while (true) {
                        System.out.println("Enter Your phone number: ");
                        long phoneNumber = Utils.getPhoneNumber();
                        System.out.println("Enter the password(default:0000): ");
                        String password = new Scanner(System.in).nextLine();
                        if (checkCredentials(phoneNumber, password)) {
                            System.out.println("Login Successfully Completed!!");
                            rider = (Rider) db.getUsers().get(phoneNumber);
                            break;
                        } else {
                            System.err.println("Incorrect credentials!!");
                        }
                    }
                    assert rider != null;
                    rider.driverFunction();
                }
                break;
            } else if (user.equalsIgnoreCase("admin")) {
                admin.driverFunction();
            }
        }

    }

    public void close() {
        System.exit(1);
    }

    @Override
    public List<Job> fetchJobList(Long phoneNumber) {
        List<Job> jobs =(List<Job>) db.getJobDatabase();
        List<Job> userHistoryOfJobs = new ArrayList<>();
        for (Job job:jobs) {
            if (job.customerNumber.equals(phoneNumber) || job.riderNumber.equals(phoneNumber)){
                userHistoryOfJobs.add(job);
            }
        }
        return userHistoryOfJobs;
    }

    @Override
    public void addRiderToDatabase(Applicant applicant) {
        Rider newRider = new Rider(applicant.getName(), applicant.getPhoneNumber(), applicant.getEmailId(), this);
        db.addUser(newRider,"0000");
    }
    @Override
    public String removeRiderFromDatabase(Rider rider) {
        if (rider.getRatings() < 1.0){
            db.getUsers().remove(rider.getPhoneNumber());
            return "Rider removed Successfully";
        }
        else{
            return "Cannot remove rider.";
        }
    }

    @Override
    public List<Rider> getAllRiders() {
        return db.getAllRiders();
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
                             int pickUpPincode, int dropPincode, Long customerNumber) {
        List<Rider> riderList = db.getRiders();
        if (riderList.size() > 0){
            Random random = new Random();
            int riderIndex = random.nextInt(riderList.size());
            Rider assignedRider = riderList.get(riderIndex);
            Job job = new Job(objectName, objectDescription, objectDimension,
                    customerNumber, pickUpPincode, dropPincode, assignedRider.getPhoneNumber());
            assignedRider.addJob(job);
            db.addJobs(job);
            db.addToCurrentlyRunningJobs(job,assignedRider.getPhoneNumber());
            sendBookingNotification(customerNumber,assignedRider.getPhoneNumber());
        }
        else {
            System.out.println("No riders available1");
        }
    }

    @Override
    public void changeJobState(Job job, String state) {
        job.setObjectState(state);
    }

    @Override
    public void changeJobState(Job job,String state, String reason) {
        job.setObjectState(state);
        sendCancelNotification(job,reason);
    }

    private void sendBookingNotification(Long customerNumber, Long riderNumber){
        db.getUsers().get(customerNumber).pushNotification(new Notification()
                        .setCustomerNotification(db.getUsers().get(riderNumber).getName()
                        + " is the rider for your service.\nRider details:\n" + db.getUsers().get(riderNumber)));
        db.getUsers().get(customerNumber).pushNotification(new Notification()
                                .setRiderNotification(db.getUsers().get(customerNumber).getName()
                                +" is the Customer who booked the service.\nCustomer details:\n"
                                +db.getUsers().get(customerNumber)));
    }
    private void sendCancelNotification(String riderName ,Job job){
        db.getUsers().get(job.customerNumber).pushNotification(new Notification()
                .setCustomerNotification(riderName + " cancelled the ride. Re-Book your service."));
    }
    private void sendCancelNotification(Job job,String reason){
        db.getUsers().get(job.customerNumber).pushNotification(new Notification()
                .setCustomerNotification(db.getUsers().get(job.riderNumber).getName()
                        +" couldn't deliver the package as ." + reason ));
    }
    @Override
    public void cancelJob(Long riderNumber,Job job) {
        Rider rider = (Rider) db.getUsers().get(riderNumber);
        db.getJobDatabase().remove(rider.getCurrentJob());
        db.getCurrentlyRunningJobs().remove(rider.getPhoneNumber());
        rider.setRatings(rider.getRatings()-0.5);
        rider.setCurrentJob(null);
        sendCancelNotification(rider.getName(),job);
    }

    public static class Job {
        private final int jobId;
        private final String objectName;
        private final String objectDescription;
        private final int objectDimension;
        private String objectState;
        private final Long customerNumber;
        private final Long riderNumber;
        private final int pickupLocation;
        private final int dropLocation;
        public int getJobId() {
            return jobId;
        }
        public void setObjectState(String state){
            this.objectState = state;
        }
        @Override
        public String toString() {
            return "objectName        =" + objectName + '\n' +
                   "objectDescription =" + objectDescription + '\n' +
                   "objectDimension   =" + objectDimension +'\n' +
                   "objectState       =" + objectState + '\n' +
                   "customerNumber    =" + customerNumber +'\n' +
                   "riderNumber       =" + riderNumber + '\n' +
                   "pickupLocation    =" + pickupLocation +'\n' +
                   "dropLocation      =" + dropLocation ;
        }

        private Job(String objectName, String objectDescription, int objectDimension,
                    Long customerNumber, int pickupLocation, int dropLocation, Long riderNumber) {
            this.jobId = this.hashCode();
            this.objectName = objectName;
            this.objectDescription = objectDescription;
            this.objectDimension = objectDimension;
            this.objectState = "Assigned";
            this.customerNumber = customerNumber;
            this.riderNumber = riderNumber;
            this.pickupLocation = pickupLocation;
            this.dropLocation = dropLocation;
        }

    }
}
