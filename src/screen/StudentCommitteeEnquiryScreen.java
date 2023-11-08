package screen;

import camp.Camp;
import camp.CampController;
import user.StudentCommittee;
import user.UserController;

public class StudentCommitteeEnquiryScreen extends StudentCommitteeScreen {
    protected Camp camp;
    StudentCommitteeEnquiryScreen(UserController userController, CampController campController, StudentCommittee studentCommittee, Camp camp) {
        super(userController, campController, studentCommittee);
        this.camp = camp;
    }

    @Override
    public Screen display() {
        System.out.println("All enquiries for camp " + camp);
        System.out.println("Options: ");
        System.out.println("0: Reply to enquiry.");
        System.out.println("9: Back.");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 9 -> new StudentCommitteeScreen(userController, campController, studentCommittee);
            default -> this;
        };
    }
}