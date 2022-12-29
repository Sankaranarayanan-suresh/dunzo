package com.users.rider;

import com.application.Application;
import com.application.UserInterface;

public interface RiderInterface extends UserInterface {
    void changeJobState(Application.Job job, String state);
    void changeJobState(Application.Job job,String state, String reason);
    void cancelJob(Long phoneNumber, Application.Job job);
}
