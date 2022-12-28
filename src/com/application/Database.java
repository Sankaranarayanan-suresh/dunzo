package com.application;

import com.users.Users;
import com.users.customer.Customer;
import com.users.rider.Rider;

import java.util.ArrayList;
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
    private final HashMap<Long, Users> userDataBase = new HashMap<>();
    private HashMap<Integer, Application.Job> jobDatabase;
    private HashMap<Application.Job, Integer> currentlyRunningJobs;
    private final HashMap<Long,String>usersCredentials = new HashMap<>();

    public void addUser(Users newUser, String password){
        this.usersCredentials.put(newUser.getPhoneNumber(),password);
        this.userDataBase.put(newUser.getPhoneNumber(),newUser);
    }
    public HashMap<Long, Users> getUsers(){
        return userDataBase;
    }
    public List<Application.Job> getJobDatabase() {
        return (List<Application.Job>) jobDatabase.values();
    }
    public List<Rider> getRiders(){
        List<Rider> availableRiders = new ArrayList<>();
        for (Users users: userDataBase.values()) {
            if (users instanceof Rider && ((Rider) users).isAvailable()){
                availableRiders.add((Rider) users);
            }
        }
        return availableRiders;
    }

    public void addJobs(Application.Job newJob){
        this.jobDatabase.put(newJob.getJobId(),newJob);
        int riderId = newJob.getRiderId();

    }

    public HashMap<Application.Job, Integer> getCurrentlyRunningJobs() {
        return currentlyRunningJobs;
    }
    public void addToCurrentlyRunningJobs(Application.Job job,Integer riderId){
        this.currentlyRunningJobs.put(job,riderId);

    }

    public HashMap<Long, String> getUsersCredentials() {
        return usersCredentials;
    }
}
