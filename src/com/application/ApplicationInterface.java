package com.application;

import com.users.admin.AdminInterface;
import com.users.customer.CustomerInterface;
import com.users.rider.RiderInterface;

public interface ApplicationInterface extends CustomerInterface, RiderInterface, AdminInterface {

}
