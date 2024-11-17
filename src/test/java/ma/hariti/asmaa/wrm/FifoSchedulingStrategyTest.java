package ma.hariti.asmaa.wrm;

import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.enumeration.Status;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FifoSchedulingStrategyTest {

    @Test
    public void testVisitCreation() {
        // Given
        Long visitorId = 1L;
        Long waitingListId = 1L;
        VisitId visitId = new VisitId(visitorId, waitingListId);
        LocalDateTime arrivalTime = LocalDateTime.now();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(9, 10);
        Status status = Status.PENDING;
        byte priority = 5;
        Duration estimatedProcessingTime = Duration.ofMinutes(10);

        // When
        Visit visit = new Visit();
        visit.setId(visitId);
        visit.setArrivalTime(arrivalTime);
        visit.setStartTime(startTime);
        visit.setEndTime(endTime);
        visit.setStatus(status);
        visit.setPriority(priority);
        visit.setEstimatedProcessingTime(estimatedProcessingTime);

        // Then
        assertNotNull(visit, "Visit should not be null");
        assertNotNull(visit.getId(), "Visit ID should not be null");
        assertEquals(visitId, visit.getId(), "Visit ID should match the provided ID");
        assertEquals(visitorId, visit.getId().getVisitorId(), "Visitor ID should match");
        assertEquals(waitingListId, visit.getId().getWaitingListId(), "Waiting List ID should match");
        assertEquals(arrivalTime, visit.getArrivalTime(), "Arrival time should match");
        assertEquals(startTime, visit.getStartTime(), "Start time should match");
        assertEquals(endTime, visit.getEndTime(), "End time should match");
        assertEquals(status, visit.getStatus(), "Status should match");
        assertEquals(priority, visit.getPriority(), "Priority should match");
        assertEquals(estimatedProcessingTime, visit.getEstimatedProcessingTime(), "Estimated processing time should match");
    }
}