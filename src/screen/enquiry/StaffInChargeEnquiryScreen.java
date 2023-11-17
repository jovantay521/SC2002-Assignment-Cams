package screen.enquiry;

import camp.Camp;
import camp.CampController;
import screen.Screen;
import screen.ScreenException;
import screen.StaffInChargeScreen;
import screen.StaffScreen;
import user.Staff;
import user.UserController;

public class StaffInChargeEnquiryScreen extends StaffScreen {
    protected Camp camp;
    public StaffInChargeEnquiryScreen(UserController userController, CampController campController, Staff staff, Camp camp) {
        super(userController, campController, staff);
        this.camp = camp;
    }

    @Override
    public Screen display() {
        System.out.println("--------------------------");
        System.out.println("All enquiries for Camp <" + camp + ">");

        var enquiries = camp.getAllEnquiries();
        displayContents(enquiries);

        System.out.println();
        System.out.println("Options: ");
        System.out.println("0: Reply to enquiry.");
        System.out.println("9: Back.");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return switch (choice) {
            case 0 -> {
                try {
                    System.out.println("Select enquiry.");
                    var enquiry = select(enquiries);
                    System.out.println("Type message to reply.");
                    var reply = scanner.nextLine();
                    enquiry.reply(reply);
                    System.out.println("Sent!");
                } catch (ScreenException e) {
                    System.out.println(e.getMessage());
                }
                yield this;
            }
            case 9 -> new StaffInChargeScreen(userController, campController, staff);
            default -> this;
        };
    }
}
