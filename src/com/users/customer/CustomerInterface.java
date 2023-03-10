package com.users.customer;

import com.application.Application;
import com.application.UserInterface;

import java.util.List;

public interface CustomerInterface extends UserInterface {
    boolean bookAService(String objectName,String objectDescription,int objectDimension,
                      int pickUpPincode,int dropPincode, Long customerNumber,String pickUpAddress,String dropAddress);
    boolean bookAService(String objectName,String objectDescription,int objectDimension,
                      int pickUpPincode,int dropPincode, Long customerNumber,double requestedRatings, String pickUpAddress,String dropAddress);
    List<Application.Job> getJobDetails(Long phoneNumber);
    double estimatedPrice(int pickUpCode,int dropPincode,int objectDimension,int rating);

}
