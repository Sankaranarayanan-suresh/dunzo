package com.users.admin;

import com.users.applicant.Applicant;
import com.users.Users;
import com.users.rider.Rider;
import com.utils.Utils;
import java.util.Scanner;

public class Admin extends Users {
    private final AdminInterface adminRequest;

    public Admin(String name, Long phoneNumber, String emailId, AdminInterface adminRequest) {
        super(phoneNumber.hashCode(), name, phoneNumber, emailId);
        this.adminRequest = adminRequest;
    }

    @Override
    public void editProfile() {
        editProfile:
        while (true) {
            System.out.println("1.Change Name\n2.Change Mail-ID\n" +
                    "3.Change password\n4.Go back to main-menu");
            int editProfilePreference = Utils.getInteger();
            switch (editProfilePreference) {
                case 1:
                    System.out.print("Enter your new name: ");
                    String newName = new Scanner(System.in).nextLine();
                    adminRequest.changeName(this.getPhoneNumber(), newName);
                    System.out.println("Your name has been changed successfully:)");
                    break;
                case 2:
                    System.out.print("Enter you new Email-ID: ");
                    String newMailId = new Scanner(System.in).nextLine();
                    adminRequest.changeMailID(this.getPhoneNumber(), newMailId);
                    System.out.println("Your EmailId has been changed successfully:)");
                    break;
                case 3:
                    System.err.println("Changing your Password can affect your LOGIN credentials too!\n" +
                            "Do you want to continue? (y/n)");
                    String passwordConfirmation = new Scanner(System.in).nextLine();
                    if (passwordConfirmation.equalsIgnoreCase("y")) {
                        System.out.println("Enter your old password: ");
                        String oldPassword = new Scanner(System.in).nextLine();
                        System.out.println("Enter your New Password: ");
                        String newPassword = new Scanner(System.in).nextLine();
                        adminRequest.changePassword(oldPassword, newPassword, this.getPhoneNumber());
                    }
                case 4:
                    break editProfile;

            }
        }
    }

    @Override
    public void viewHistory() {
        System.out.println("Ypu have Logged in as admin. You Don't have any history.");
    }

    private void addRider() {
        if (adminRequest.getAllApplicants().size() > 0) {
            int i = 0;
            System.out.println(adminRequest.getAllApplicants().size());
            while (!adminRequest.getAllApplicants().isEmpty()) {
                if (i >= adminRequest.getAllApplicants().size())
                    break;
                Applicant applicant = adminRequest.getAllApplicants().get(i);
                System.out.println(applicant);
                System.out.println("1.Add to rider database\n2.Remove from the list\n3.Skip");
                int adminDecision = Utils.getInteger();
                if (adminDecision == 3) {
                    i++;
                    continue;
                }
                if (adminDecision == 1) {
                    adminRequest.addRiderToDatabase(applicant);
                    System.out.println("Rider Added to database.");
                } else {
                    System.out.println("Rider removed from database.");
                    adminRequest.removeApplicant(applicant);
                }
            }

        } else {
            System.out.println("\nNo riders to add\n");
        }
    }

    private void removeRider() {
        if (adminRequest.getAllRiders().size() > 0) {
            int i = 1;
            for (Rider rider : adminRequest.getAllRiders()) {
                System.out.println(i + "." + rider.getName() + ": " + rider.getRatings());
            }
            System.out.println("Select the rider you want to remove! or press 0 to exit");
            int riderIndex = Utils.getInteger();
            if (riderIndex > 0) {
                try {
                    Rider rider = adminRequest.getAllRiders().get(riderIndex - 1);
                    String comment = adminRequest.removeRiderFromDatabase(rider);
                    System.out.println(comment);
                } catch (Exception e) {
                    System.out.println("You cannot remove a rider who does not exist.\nPlease select from given option.");
                    removeRider();
                }
            }
        } else {
            System.out.println("\nNo rider to remove!!\n");
        }
    }

    public String toString() {
        return "AdminId      :" + this.getId() + "\n" +
                "AdminName    :" + this.getName() + "\n" +
                "phoneNumber  :" + this.getPhoneNumber() + "\n" +
                "emailId      :" + this.getEmailId() + "\n";
    }

    @Override
    public void showMenu() {
        System.out.println("You have " + notifications.size() + " number of notifications.");
        adminDriverFunction:
        while (true) {
            System.out.println("1.Edit Profile\n2.Add Riders\n3.Remove Rider\n4.View Notifications\n5.View my Profile\n" +
                    "6.Sign-Out");
            int adminPreference = Utils.getInteger();
            switch (adminPreference) {
                case 1:
                    editProfile();
                    break;
                case 2:
                    addRider();
                    break;
                case 3:
                    removeRider();
                    break;
                case 4:
                    viewNotification();
                    break;
                case 5:
                    viewProfile();
                    break;
                case 6:
                    break adminDriverFunction;
            }
        }

    }
}
