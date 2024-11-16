package ma.hariti.asmaa.wrm;

import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.enumeration.Status;
import ma.hariti.asmaa.wrm.repository.SchedulingStrategy;
import ma.hariti.asmaa.wrm.service.algorithm.PrioritySchedulingStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class PrioritySchedulingStrategyTest {

    private void printVisitDetails(String message, List<Visit> visits) {
        System.out.println("\n" + message);
        System.out.println("----------------------------------------");
        for (Visit visit : visits) {
            System.out.printf("Visit ID: %d | Arrival: %s | Priority: %d | Processing Time: %s minutes%n",
                    visit.getId().getValue(),
                    visit.getArrivalTime(),
                    visit.getPriority(),
                    visit.getEstimatedProcessingTime().toMinutes());
        }
        System.out.println("----------------------------------------\n");
    }

    @Test
    public void testPrioritySchedulingStrategy() {
        // Arrange
        VisitId id1 = new VisitId();
        id1.setValue(1L);
        VisitId id2 = new VisitId();
        id2.setValue(2L);
        VisitId id3 = new VisitId();
        id3.setValue(3L);

        LocalDateTime now = LocalDateTime.now();

        Visit visit1 = new Visit(id1, now.minusHours(1),
                LocalTime.of(9, 0), LocalTime.of(9, 10),
                Status.PENDING, (byte) 5, Duration.ofMinutes(10), null, null);

        Visit visit2 = new Visit(id2, now.minusHours(4),
                LocalTime.of(8, 30), LocalTime.of(8, 33),
                Status.PENDING, (byte) 3, Duration.ofMinutes(8), null, null);

        Visit visit3 = new Visit(id3, now.minusMinutes(30),
                LocalTime.of(9, 15), LocalTime.of(9, 27),
                Status.PENDING, (byte) 7, Duration.ofMinutes(12), null, null);

        List<Visit> visits = Arrays.asList(visit1, visit2, visit3);

        printVisitDetails("Initial Visit Queue Before Scheduling:", visits);

        List<String> priorityFactors = Arrays.asList("waiting-time", "urgency", "severity");
        SchedulingStrategy priorityStrategy = new PrioritySchedulingStrategy(priorityFactors);

        // Act
        List<Visit> sortedVisits = priorityStrategy.schedule(visits);

        printVisitDetails("Final Visit Queue After Priority Scheduling:", sortedVisits);

        // Assert
        assertEquals(id2, sortedVisits.get(0).getId());
        assertEquals(id3, sortedVisits.get(1).getId());
        assertEquals(id1, sortedVisits.get(2).getId());

        assertTrue(sortedVisits.get(0).getPriority() > sortedVisits.get(1).getPriority(),
                "First visit should have higher priority than second");
        assertTrue(sortedVisits.get(1).getPriority() > sortedVisits.get(2).getPriority(),
                "Second visit should have higher priority than third");
    }

    @Test
    public void testPrioritySchedulingWithEqualPriorities() {
        // Arrange
        VisitId id1 = new VisitId();
        id1.setValue(1L);
        VisitId id2 = new VisitId();
        id2.setValue(2L);

        LocalDateTime now = LocalDateTime.now();

        Visit visit1 = new Visit(id1, now.minusHours(1),
                LocalTime.of(9, 0), LocalTime.of(9, 10),
                Status.PENDING, (byte) 5, Duration.ofMinutes(10), null, null);

        Visit visit2 = new Visit(id2, now.minusHours(2),
                LocalTime.of(8, 30), LocalTime.of(8, 33),
                Status.PENDING, (byte) 5, Duration.ofMinutes(8), null, null);

        List<Visit> visits = Arrays.asList(visit1, visit2);

        printVisitDetails("Initial Visit Queue Before Scheduling (Equal Priorities):", visits);

        List<String> priorityFactors = Arrays.asList("waiting-time", "urgency", "severity");
        SchedulingStrategy priorityStrategy = new PrioritySchedulingStrategy(priorityFactors);

        // Act
        List<Visit> sortedVisits = priorityStrategy.schedule(visits);

        printVisitDetails("Final Visit Queue After Priority Scheduling (Equal Priorities):", sortedVisits);

        // Assert
        assertEquals(id2, sortedVisits.get(0).getId(),
                "Visit with longer waiting time should be first");
        assertEquals(id1, sortedVisits.get(1).getId(),
                "Visit with shorter waiting time should be second");
    }
}