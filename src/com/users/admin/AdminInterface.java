package com.users.admin;

import com.application.UserInterface;
import com.users.rider.Rider;

public interface AdminInterface extends UserInterface {
    public void addRiderToDatabase(Rider newRider, String password);
}
