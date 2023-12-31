package utils;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a region in time, with a start and an end.
 */
public class TimeRegion implements Serializable {
    /**
     * StartDate
     */
    private LocalDate start;
    /**
     * EndDate
     */
    private LocalDate end;

    /**
     * TimeRegion Constructor
     * @param start StartDate
     * @param end EndDate
     */
    public TimeRegion(LocalDate start, LocalDate end) {
        this.start = start; this.end = end;
    }

    /**
     * Start time
     * @return Local start time
     */
    public LocalDate getStartTime()
    {
    	return start;
    }

    /**
     * Adjust Start of Region
     * @param start New start of the region.
     */
    public void adjustStart(LocalDate start) {
        this.start = start;
    }

    /**
     * Adjust End of Region
     * @param end New end of the region.
     */
    public void adjustEnd(LocalDate end) {
        this.end = end;
    }

    /**
     * Check if there is a conflict (overlap) in time.
     * @param region Region to compare against
     * @return True if there is a conflict, false otherwise.
     */
    public boolean conflictsWith(TimeRegion region) {
        return (start.isBefore(region.end) || start.isEqual(region.end)) && (end.isAfter(region.start) || end.isEqual(region.start));
    }

    /**
     * Check if there is full coverage in time
     * @param region Region to compare against
     * @return True if there is full coverage, false otherwise
     */
    public boolean fullyCover(TimeRegion region) {
        return (start.isEqual(start) || start.isBefore(region.start)) && (end.isEqual(end) || end.isAfter(region.end));
    }

    @Override
    public String toString() {
        return "[From: " + start + ", End: " + end + "]";
    }
}
