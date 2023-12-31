package camp;

import user.Staff;
import user.Student;
import user.User;
import user.UserController;
import utils.TimeRegion;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller class of the Camp class.
 */
public class CampController
{
	/**
	 * Initialise a list of Camps.
	 */
    private final List<Camp> camps = new ArrayList<>();

    /**
     * Filter the Camp using date.
     * @param timeRegion Date range (From Date to Date).
     * @return Camp that is within the date range.
     */
    public static Filter DateFilter(TimeRegion timeRegion)
    {

        return (Camp camp) -> timeRegion.fullyCover(camp.getRegion());
    }
    
    /**
     * Filter the Camp using location.
     * @param location Camp location chose by student.
     * @return Camp that is held at the location chose by student.
     */
    public static Filter LocationFilter(String location)
    {
        return (Camp camp) -> camp.getLocation().equals(location);
    }

    /**
     * Filter interface
     */
    public interface Filter {
        /**
         * Takes a camp as input and returns whether it should be filtered
         * @param camp Camp to be filtered
         * @return whether it should be filtered
         */
        boolean accept(Camp camp);
    }

    /**
     * Returns list of camps that can be viewed by user.
     * @param user object.
     * @return The list of camps that can be viewed by user.
     */
    public List<Camp> getVisibleCamps(User user)
    {
        return getVisibleCamps(user, user.getFilters().values().stream().toList());
    }

    /**
     *  Returns list of camps that can be viewed by user with a filter.
     * @param user User object.
     * @param filters Filter selected by the users.
     * @return The list of camps that can be viewed by user with a filter.
     */
    public List<Camp> getVisibleCamps(User user, List<Filter> filters)
    {
        var visibleCamps = camps.stream().filter(camp -> camp.isVisible(user));
        for (Filter filter: filters)
            visibleCamps = visibleCamps.filter(filter::accept);

        visibleCamps = visibleCamps.sorted((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        return visibleCamps.collect(Collectors.toList());
    }
    
    /**
     *  Returns list of camps that belongs to staff.
     * @param staff Staff object.
     * @return The list of camps that belongs to staff.
     */
    public List<Camp> getInChargeCamps(Staff staff)
    {
        return camps.stream().filter(camp -> camp.isInCharge(staff)).collect(Collectors.toList());
    }
    /**
     * creates a camp
     * @param staff Staff object.
     * @param campName The name of this camp.
     * @param region The duration (start and end date) of this camp.
     * @param regCloseDate The registration deadline of this camp.
     * @param userGroup Which group(faculty) of students is this camp open to.
     * @param location Location where this camp will be held at.
     * @param totalSlots Number of total slots for this camp (Camp attendee + Camp committee slots).
     * @param campCommitteeSlot Number of camp committee slots for this camp.
     * @param description Description of this camp.
     * @return Camp object that is created.
     */
    public Camp createCamp(Staff staff, String campName, TimeRegion region, LocalDate regCloseDate, String userGroup, String location, int totalSlots, int campCommitteeSlot, String description)
    {
        Camp newCamp = new Camp(campName, region, regCloseDate, userGroup, location, totalSlots, campCommitteeSlot, description, staff, true);
        camps.add(newCamp);
        return newCamp;
    }
    
    /**
     * Delete the camp object.
     * @param staff Staff object that is using the CAM system to delete the camp.
     * @param camp Target Camp object that the Staff want to delete.
     * @param userController Controller class of the User class.
     * @throws CampControllerException Display the reason why this camp cannot be deleted.
     */
    public void deleteCamp(Staff staff, Camp camp, UserController userController) throws CampControllerException
    {
        if (!camp.isOwner(staff)) {
            throw new CampControllerException("Not the owner you cannot remove it.");
        }
        var users = userController.getUsers(camp.getStudentNames());
        for (var user: users) {
            if (user instanceof Student student) {
                student.removeCamp(camp);
            }
        }
        camps.remove(camp);
    }
}
