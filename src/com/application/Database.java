package com.application;

import com.users.applicant.Applicant;
import com.users.Users;
import com.users.rider.Rider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Database {
    private static Database databaseInstance = null;
    public static Database getInstance() {
        if (databaseInstance == null) {
            databaseInstance = new Database();
        }
        return databaseInstance;
    }


    private Database() {
    }
    private final List<String> nonTransferableThings = new ArrayList<String>(){{
       add("jewel");
       add("cash");
       add("phone");
       add("laptop");
    }};
    private final HashMap<Long, Users> userDataBase = new HashMap<>();
    private final HashMap<Integer, Application.Job> jobDatabase  = new HashMap<>();
    private final HashMap<Long,Application.Job> currentlyRunningJobs = new HashMap<>();
    private final HashMap<Long,String>usersCredentials = new HashMap<>();
    private final List<Applicant> approvalList = new ArrayList<>();

    public void addUser(Users newUser, String password){
        this.usersCredentials.put(newUser.getPhoneNumber(),password);
        this.userDataBase.put(newUser.getPhoneNumber(),newUser);
    }
    public void addToApprovalList(Applicant newApplicant){
        approvalList.add(newApplicant);
    }
    public List<Applicant> getApplicants(){
        return approvalList;
    }
    public List<String> getNonTransferableThings(){
        return nonTransferableThings;
    }
    public HashMap<Long, Users> getUsers(){
        return userDataBase;
    }
    public Collection<Application.Job> getJobDatabase() {
        return jobDatabase.values();
    }
    public List<Rider> getRiders(int pickUpPincode,int dropPincode) {
        List<Rider> availableRiders = new ArrayList<>();
        for (Users users : userDataBase.values()) {
            if (users instanceof Rider && ((Rider) users).isAvailable()) {
                if (Math.abs(((Rider) users).getServiceLocation() - pickUpPincode) <= 100
                        && Math.abs(((Rider) users).getServiceLocation() - dropPincode) <= 100) {

                                availableRiders.add((Rider) users);

                }
            }
        }
        return availableRiders;
    }
    public List<Rider> getAllRiders(){
        List<Rider> allRiders = new ArrayList<>();
        for (Users users: userDataBase.values()) {
            if (users instanceof Rider){
                allRiders.add((Rider) users);
            }
        }
        return allRiders;
    }

    public void addJobs(Application.Job newJob){
        this.jobDatabase.put(newJob.getJobId(),newJob);

    }

    public HashMap<Long,Application.Job> getCurrentlyRunningJobs() {
        return currentlyRunningJobs;
    }
    public void addToCurrentlyRunningJobs(Application.Job job,Long phoneNumber){
        this.currentlyRunningJobs.put(phoneNumber,job);
    }

    public HashMap<Long, String> getUsersCredentials() {
        return usersCredentials;
    }
}
