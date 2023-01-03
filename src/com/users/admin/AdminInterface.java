package com.users.admin;

import com.application.Application;
import com.application.UserInterface;
import com.users.applicant.Applicant;
import com.users.rider.Rider;

import java.util.List;

public interface AdminInterface extends UserInterface {
     void addRiderToDatabase(Applicant applicant);
     String removeRiderFromDatabase(Rider rider);
     List<Applicant> getAllApplicants();
     List<Rider> getAllRiders();
     void removeApplicant(Applicant applicant);
}
