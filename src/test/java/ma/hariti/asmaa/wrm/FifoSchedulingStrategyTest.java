package ma.hariti.asmaa.wrm;

import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.enumeration.Status;
import ma.hariti.asmaa.wrm.repository.SchedulingStrategy;
import ma.hariti.asmaa.wrm.service.algorithm.FifoSchedulingStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class FifoSchedulingStrategyTest {

    @Test
    public void testFifoSchedulingStrategy() {
        // Arrange
        VisitId id1 = new VisitId();
        id1.setValue(1L);
        VisitId id2 = new VisitId();
        id2.setValue(2L);
        VisitId id3 = new VisitId();
        id3.setValue(3L);

        List<Visit> visits = Arrays.asList(
                new Visit(id1, LocalDateTime.of(2024, 11, 1, 9, 0), LocalTime.of(9, 0), LocalTime.of(9, 10), Status.PENDING, (byte) 5, Duration.ofMinutes(10), null, null),
                new Visit(id2, LocalDateTime.of(2024, 11, 1, 8, 30), LocalTime.of(8, 30), LocalTime.of(8, 33), Status.PENDING, (byte) 3, Duration.ofMinutes(8), null, null),
                new Visit(id3, LocalDateTime.of(2024, 11, 1, 9, 15), LocalTime.of(9, 15), LocalTime.of(9, 27), Status.PENDING, (byte) 7, Duration.ofMinutes(12), null, null)
        );

        SchedulingStrategy fifoStrategy = new FifoSchedulingStrategy();

        System.out.println("Original Order:");
        visits.forEach(visit -> System.out.println("VisitId: " + visit.getId().getValue() + ", Start Time: " + visit.getStartTime()));

        // Act
        List<Visit> sortedVisits = fifoStrategy.schedule(visits);

        System.out.println("Sorted Order:");
        sortedVisits.forEach(visit -> System.out.println("VisitId: " + visit.getId().getValue() + ", Start Time: " + visit.getStartTime()));

        Assertions.assertEquals(id2, sortedVisits.get(0).getId());
        Assertions.assertEquals(id1, sortedVisits.get(1).getId());
        Assertions.assertEquals(id3, sortedVisits.get(2).getId());
    }
}
