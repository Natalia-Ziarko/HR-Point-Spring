package com.point.hr.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class LeaveRequestFormDTO {
    private static final String INFO_WHEN_REQUIRED = "is required";
    private static final String INFO_WHEN_END_DATE_TOO_EARLY = "End date cannot be earlier than start date";

    @NotNull(message = INFO_WHEN_REQUIRED)
    private Integer personId;

    @NotNull(message = INFO_WHEN_REQUIRED)
    private Integer leaveTypeId;

    @NotNull(message = INFO_WHEN_REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = INFO_WHEN_REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    // pól, których TU nie ma - i to jest clou refaktoru:
    // id, durationDays, whoAddedId, whenAdded, leaveRequestStatuses
    // -> wszystkie ustawiane wyłącznie przez serwis, po stronie serwera

    @AssertTrue(message = INFO_WHEN_END_DATE_TOO_EARLY)
    public boolean isEndDateAfterStartDate() {
        if (startDate == null || endDate == null)
            return true;

        return !endDate.isBefore(startDate);
    }
}
