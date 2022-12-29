package com.users.admin;

import com.application.UserInterface;
import com.users.Applicant;
import com.users.rider.Rider;

import java.util.List;

public interface AdminInterface extends UserInterface {
     void addRiderToDatabase(Applicant applicant);
     String removeRiderFromDatabase(Rider rider);
     List<Rider> getAllRiders();
}
