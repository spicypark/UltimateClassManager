package backend.model;

import java.time.LocalDate;

/**
 * Represents a teacher break period between a start and end date (inclusive).
 */
public class TeacherBreak {
    private LocalDate startDate;
    private LocalDate endDate;

    public TeacherBreak(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Checks if the given date falls within this break period (inclusive).
     */
    public boolean containsDate(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
