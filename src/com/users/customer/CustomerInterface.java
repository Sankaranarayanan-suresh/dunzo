package com.users.customer;

import com.application.UserInterface;

public interface CustomerInterface extends UserInterface {
    void bookAService(String objectName,String objectDescription,int objectDimension,
                      int pickUpPincode,int dropPincode, Long customerNumber );
    void bookAService(String objectName,String objectDescription,int objectDimension,
                      int pickUpPincode,int dropPincode, Long customerNumber,double requestedRatings );

}
