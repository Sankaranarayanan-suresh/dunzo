package com.application;

import com.notification.Notification;
import com.users.applicant.Applicant;
import com.users.admin.Admin;
import com.users.customer.Customer;
import com.users.rider.Rider;
import com.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Application implements ApplicationInterface {
    Database db;
    Admin admin;
    Customer customer;
    Rider rider;

    public Application() {
        this.db = Database.getInstance();
        this.admin = new Admin("sankar", 1234567890L, "qwerty", this);
        this.customer = new Customer("shiva",9876543211L,"asdad",this);
        this.rider = new Rider("shakthi",1234567888L,"Aafdf",1,this);
        db.addUser(admin, "0000");
        db.addUser(customer,"0000");
        db.addUser(rider,"0000");
    }

    public void open(String user) {
        if (user.equalsIgnoreCase("customer")) {
            while (true) {
                System.out.println("1.SignUp\n2.SignIn\n3.Go back to the previous menu");
                int preference = Utils.getInteger();
                Customer customer;
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
                        if (!password.equals(reenteredPassword)) {
                            System.err.println("Password Mismatch");
                            continue;
                        }
                        customer = new Customer(name, phoneNumber, mail, this);
                        db.addUser(customer, password);
                        System.out.println("\n\nSignUp Successful!!");
                        break;
                    }

                } else if (preference == 2) {
                    while (true) {
                        System.out.println("Enter Your phone number: ");
                        long phoneNumber = Utils.getPhoneNumber();
                        System.out.println("Enter the password: ");
                        String password = new Scanner(System.in).nextLine();
                        if (db.getUsers().get(phoneNumber) instanceof Customer) {
                            if (checkCredentials(phoneNumber, password)) {
                                System.out.println("\n\nLogin Successfully Completed!!");
                                customer = (Customer) db.getUsers().get(phoneNumber);
                                customer.showMenu();
                                break;
                            } else {
                                System.err.println("Incorrect credentials!!");
                            }
                        } else {
                            System.err.println("No such User exists!!");
                            break;
                        }
                    }
                } else if (preference == 3) {
                    break;
                } else {
                    System.err.println("Enter correct Option!!!");
                }
            }

        } else if (user.equalsIgnoreCase("rider")) {
            while (true) {
                System.out.println("1.Apply for rider\n2.SignIn\n3.Exit");
                int preference = Utils.getInteger();
                Rider rider;
                if (preference == 1) {
                    System.out.println("Enter Your name: ");
                    String name = new Scanner(System.in).nextLine();
                    System.err.println("Remember the phone number that you enter now." +
                            " \nYou will be using your phone number to login again.");
                    System.out.println("Enter Your phone number: ");
                    long phoneNumber = Utils.getPhoneNumber();
                    if (db.getUsersCredentials().containsKey(phoneNumber)) {
                        System.out.println("User already Exists!!!");
                        break;
                    }
                    System.out.println("Enter Your Email-ID: ");
                    String mail = new Scanner(System.in).nextLine();
                    System.out.println("Enter your service location: ");
                    int serviceLocation = Utils.getInteger();
                    Applicant applicant = new Applicant(name, phoneNumber, serviceLocation, mail);
                    db.addToApprovalList(applicant);
                    db.getUsers().get(admin.getPhoneNumber()).pushNotification(new Notification()
                            .setNotificationMessage(applicant.getName() + " has requested as rider."));
                    System.out.println("\nYour application has been submitted!\n" +
                            "Use the Given phone Number and '0000' as password to login to know about your application status.");
                } else if (preference == 2) {
                    while (true) {
                        System.out.println("Enter Your phone number: ");
                        long phoneNumber = Utils.getPhoneNumber();
                        System.out.println("Enter the password(default:0000): ");
                        String password = new Scanner(System.in).nextLine();
                        if (db.getUsers().get(phoneNumber) instanceof Rider) {
                            if (checkCredentials(phoneNumber, password)) {
                                System.out.println("\n\nLogin Successfully Completed!!");
                                rider = (Rider) db.getUsers().get(phoneNumber);
                                rider.showMenu();
                                break;
                            } else {
                                System.err.println("Incorrect credentials!!");
                            }
                        } else {
                            System.err.println("No such User exists!!");
                            break;
                        }
                    }
                } else if (preference == 3) {
                    break;
                }
            }
        } else if (user.equalsIgnoreCase("admin")) {
            while (true) {
                System.out.println("Enter Your phone number: ");
                long phoneNumber = Utils.getPhoneNumber();
                System.out.println("Enter the password(default:0000): ");
                String password = new Scanner(System.in).nextLine();
                if (db.getUsers().get(phoneNumber) instanceof Admin) {
                    if (checkCredentials(phoneNumber, password)) {
                        System.out.println("\n\nLogin Successfully Completed!!\n");
                        admin = (Admin) db.getUsers().get(phoneNumber);
                        admin.showMenu();
                        break;
                    } else {
                        System.err.println("Incorrect credentials!!");
                    }
                } else {
                    System.err.println("No such User exists!!");
                    break;
                }
            }
        }
    }


    public void close() {
        System.exit(1);
    }

    @Override
    public List<Job> fetchJobList(Long phoneNumber) {
        List<Job> jobs = new ArrayList<>(db.getJobDatabase());
        List<Job> userHistoryOfJobs = new ArrayList<>();
        for (Job job : jobs) {
            if (job.customerNumber.equals(phoneNumber) || job.riderNumber.equals(phoneNumber)) {
                userHistoryOfJobs.add(job);
            }
        }
        return userHistoryOfJobs;
    }

    @Override
    public void addRiderToDatabase(Applicant applicant) {
        Rider newRider = new Rider(applicant.getName(), applicant.getPhoneNumber(), applicant.getEmailId(), applicant.getServiceLocation(), this);
        db.addUser(newRider, "0000");
        db.getUsers().get(newRider.getPhoneNumber()).pushNotification(new Notification()
                .setNotificationMessage("Hey " + newRider.getName() + "!!!\nWelcome to Dunzo Family!!\nYour application as Rider has been accepted."));
        db.getApplicants().remove(applicant);
    }

    @Override
    public String removeRiderFromDatabase(Rider rider) {
        if (rider.getRatings() < 1.0) {
            db.getUsers().remove(rider.getPhoneNumber());
            db.getUsersCredentials().remove(rider.getPhoneNumber());
            return "Rider removed Successfully";
        } else {
            return "Cannot remove rider.";
        }
    }

    @Override
    public List<Applicant> getAllApplicants() {
        return db.getApplicants();
    }

    @Override
    public List<Rider> getAllRiders() {
        return db.getAllRiders();
    }

    @Override
    public void removeApplicant(Applicant applicant) {
        db.getApplicants().remove(applicant);
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
    public void changePassword(String oldPassword, String newPassword, long phoneNumber) {
        if (checkCredentials(phoneNumber, oldPassword)) {
            db.getUsersCredentials().replace(phoneNumber, newPassword);
            System.out.println("Password successfully changed:)");
        } else {
            System.err.println("Password Mismatch!!!");
        }
    }

    @Override
    public boolean bookAService(String objectName, String objectDescription, int objectDimension,
                                int pickUpPincode, int dropPincode, Long customerNumber,String pickUpAddress,String dropAddress) {
        List<Rider> riderList = db.getRiders(pickUpPincode, dropPincode);
        if (riderList.size() == 0) {
            System.out.println("No riders available");
            return false;
        }

        Random random = new Random();
        int riderIndex = random.nextInt(riderList.size());
        Rider assignedRider = riderList.get(riderIndex);

        if (checkObjectEligibility(objectName.toLowerCase())) {
            System.err.println("Object cannot be transferred!!");
            return false;
        }

        Job job = new Job(objectName, objectDescription, objectDimension,
                customerNumber, pickUpPincode, dropPincode, assignedRider.getPhoneNumber(),pickUpAddress,dropAddress);
        assignedRider.addJob(job);
        db.addJobs(job);
        db.addToCurrentlyRunningJobs(job, assignedRider.getPhoneNumber());
        sendBookingNotification(customerNumber, assignedRider.getPhoneNumber(), job);
        Customer customer = (Customer) db.getUsers().get(customerNumber);
        double customerRating = customer.requestRating();
        Rider rider = (Rider) db.getUsers().get(assignedRider.getPhoneNumber());
        rider.setRatings((customerRating/100)*5);
        return true;
    }

    private boolean checkObjectEligibility(String objectName) {
        return db.getNonTransferableThings().contains(objectName);
    }

    @Override
    public boolean bookAService(String objectName, String objectDescription, int objectDimension,
                                int pickUpPincode, int dropPincode, Long customerNumber, double requestedRatings,String pickUpAddress,String dropAddress) {
        List<Rider> riderList = db.getRiders(pickUpPincode, dropPincode);
        if (riderList.size() == 0) {
            System.out.println("No riders available near your location.");
            return false;
        }
        List<Rider> eligibleRiders = new ArrayList<>();
        for (Rider rider : riderList) {
            if (rider.getRatings() >= requestedRatings)
                eligibleRiders.add(rider);
        }
        if (eligibleRiders.size() == 0) {
            System.out.println("No riders available with that specific rating in that location.");
            return false;
        }
        Random random = new Random();
        int riderIndex = random.nextInt(riderList.size());
        Rider assignedRider = riderList.get(riderIndex);
        if (checkObjectEligibility(objectName)) {
            System.err.println("Object cannot be transferred!!");
            return false;
        }
        Job job = new Job(objectName, objectDescription, objectDimension,
                customerNumber, pickUpPincode, dropPincode, assignedRider.getPhoneNumber(),pickUpAddress,dropAddress);
        assignedRider.addJob(job);
        db.addJobs(job);
        db.addToCurrentlyRunningJobs(job, assignedRider.getPhoneNumber());
        sendBookingNotification(customerNumber, assignedRider.getPhoneNumber(), job);
        return true;
    }

    @Override
    public List<Job> getJobDetails(Long phoneNumber) {
        List<Job> jobs = new ArrayList<>(db.getCurrentlyRunningJobs().values());
        List<Job> CurrentJobs = new ArrayList<>();
        for (Job job : jobs) {
            if (job.customerNumber.equals(phoneNumber) || job.riderNumber.equals(phoneNumber)) {
                CurrentJobs.add(job);
            }
        }
        return CurrentJobs;
    }

    @Override
    public double estimatedPrice(int pickUpCode, int dropPincode, int objectDimension, int rating) {
        switch (rating) {
            case 1:
                return feeCalculator(pickUpCode, dropPincode, objectDimension, 100);
            case 2:
                return feeCalculator(pickUpCode, dropPincode, objectDimension, 250);
            case 3:
                return feeCalculator(pickUpCode, dropPincode, objectDimension, 400);
            case 4:
                return feeCalculator(pickUpCode, dropPincode, objectDimension, 750);
            case 5:
                return feeCalculator(pickUpCode, dropPincode, objectDimension, 1000);
            default:
                return feeCalculator(pickUpCode, dropPincode, objectDimension, 50);
        }
    }

    private double feeCalculator(int pickUpPincode, int dropPincode, int objectDimension, double riderFee) {
        double ratePerKm = 12;
        if (objectDimension <= 500) {
            ratePerKm = 4;
        } else if (objectDimension <= 1000) {
            ratePerKm = 5;
        } else if (objectDimension <= 1500) {
            ratePerKm = 7;
        } else if (objectDimension <= 2000) {
            ratePerKm = 9;
        }
        double actualPrice = ((Math.abs((pickUpPincode - dropPincode)) * ratePerKm )+ riderFee);
        System.out.println("Estimated Total price: Rs." + actualPrice);
        return actualPrice;
    }

    @Override
    public void changeJobState(Job job, String state) {
        job.setObjectState(state);
        if (state.equals("Delivered")) {
            freeRider(job.riderNumber);
            db.getCurrentlyRunningJobs().remove(job.riderNumber);
            sendTaskCompleteNotification(job.customerNumber,job.riderNumber,job);

        }
    }

    private void freeRider(Long phoneNumber) {
        Rider rider = (Rider) db.getUsers().get(phoneNumber);
        rider.setCurrentJob(null);
        rider.setAvailable(true);
    }

    @Override
    public void changeJobState(Job job, String state, String reason) {
        job.setObjectState(state);
        sendCancelNotification(job, reason);
        freeRider(job.riderNumber);
        db.getCurrentlyRunningJobs().remove(job.riderNumber);
    }

    private void sendBookingNotification(Long customerNumber, Long riderNumber, Job job) {
        db.getUsers().get(customerNumber).pushNotification(new Notification()
                .setNotificationMessage(db.getUsers().get(riderNumber).getName()
                        + " is the rider for your service.\nRider details:\n" + db.getUsers().get(riderNumber) + "\n Job Details:\n"
                        + job.toString()));
        db.getUsers().get(riderNumber).pushNotification(new Notification()
                .setNotificationMessage(db.getUsers().get(customerNumber).getName()
                        + " is the Customer who booked the service.\nCustomer details:\n"
                        + db.getUsers().get(customerNumber)));
    }
    private void sendTaskCompleteNotification(Long customerNumber, Long riderNumber, Job job) {
        db.getUsers().get(customerNumber).pushNotification(new Notification()
                .setNotificationMessage("The item has been successfully picked up from "+ job.pickUpAddress +" and delivered to " +
                        job.dropAddress + " by "+ db.getUsers().get(riderNumber).getName()));
        db.getUsers().get(customerNumber).pushNotification(new Notification()
                .setNotificationMessage("Your Ride has been completed."));
    }

    private void sendCancelNotification(String riderName, Job job) {
        db.getUsers().get(job.customerNumber).pushNotification(new Notification()
                .setNotificationMessage(riderName + " cancelled the ride. Re-Book your service."));
    }

    private void sendCancelNotification(Job job, String reason) {
        db.getUsers().get(job.customerNumber).pushNotification(new Notification()
                .setNotificationMessage(db.getUsers().get(job.riderNumber).getName()
                        + " couldn't deliver the package as ." + reason));
    }

    @Override
    public void cancelJob(Long riderNumber, Job job) {
        Rider rider = (Rider) db.getUsers().get(riderNumber);
        db.getJobDatabase().remove(rider.getCurrentJob());
        db.getCurrentlyRunningJobs().remove(rider.getPhoneNumber());
        rider.setRatings(rider.getRatings() - 0.5);
        rider.setCurrentJob(null);
        sendCancelNotification(rider.getName(), job);
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
        private final String pickUpAddress;
        private final String dropAddress;

        public int getJobId() {
            return jobId;
        }

        public void setObjectState(String state) {
            this.objectState = state;
        }

        @Override
        public String toString() {
            return "objectName         :" + objectName + '\n' +
                    "objectDescription :" + objectDescription + '\n' +
                    "objectDimension   :" + objectDimension + '\n' +
                    "objectState       :" + objectState + '\n' +
                    "customerNumber    :" + customerNumber + '\n' +
                    "riderNumber       :" + riderNumber + '\n' +
                    "pickupLocation    :" + pickupLocation + '\n' +
                    "dropLocation      :" + dropLocation;
        }

        private Job(String objectName, String objectDescription, int objectDimension,
                    Long customerNumber, int pickupLocation, int dropLocation, Long riderNumber, String pickUpAddress, String dropAddress) {
            this.jobId = this.hashCode();
            this.objectName = objectName;
            this.objectDescription = objectDescription;
            this.objectDimension = objectDimension;
            this.objectState = "Assigned";
            this.customerNumber = customerNumber;
            this.riderNumber = riderNumber;
            this.pickupLocation = pickupLocation;
            this.dropLocation = dropLocation;
            this.pickUpAddress = pickUpAddress;
            this.dropAddress = dropAddress;
        }

        public String getObjectState() {
            return objectState;
        }
    }
}
