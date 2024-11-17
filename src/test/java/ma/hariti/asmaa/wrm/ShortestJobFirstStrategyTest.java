package ma.hariti.asmaa.wrm;

import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.service.algorithm.ShortestJobFirstStrategy;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortestJobFirstStrategyTest {

    @Test
    public void testSchedule() {
        VisitId id1 = new VisitId(1L, 101L);
        VisitId id2 = new VisitId(2L, 102L);
        VisitId id3 = new VisitId(3L, 103L);
        VisitId id4 = new VisitId(4L, 104L);

        Visit visit1 = new Visit(id1, Duration.ofHours(5));
        Visit visit2 = new Visit(id2, Duration.ofHours(2));
        Visit visit3 = new Visit(id3, Duration.ofHours(10));
        Visit visit4 = new Visit(id4, Duration.ofHours(1));

        List<Visit> visits = Arrays.asList(visit1, visit2, visit3, visit4);

        System.out.println("Original Order of Visits:");
        System.out.println("-------------------------");
        for (Visit visit : visits) {
            System.out.printf("Visit ID: %d-%d, Processing Time: %d hours%n",
                    visit.getId().getVisitorId(),
                    visit.getId().getWaitingListId(),
                    visit.getEstimatedProcessingTime().toHours());
        }
        System.out.println();

        ShortestJobFirstStrategy strategy = new ShortestJobFirstStrategy();
        List<Visit> scheduledVisits = strategy.schedule(visits);

        System.out.println("Scheduled Order of Visits (SJF):");
        System.out.println("--------------------------------");
        for (int i = 0; i < scheduledVisits.size(); i++) {
            Visit visit = scheduledVisits.get(i);
            System.out.printf("Position %d: Visit ID: %d-%d, Processing Time: %d hours%n",
                    i + 1,
                    visit.getId().getVisitorId(),
                    visit.getId().getWaitingListId(),
                    visit.getEstimatedProcessingTime().toHours());
        }
        System.out.println();

        // Assertions
        assertEquals(Duration.ofHours(1), scheduledVisits.get(0).getEstimatedProcessingTime(),
                "First visit should be 1 hour");
        assertEquals(Duration.ofHours(2), scheduledVisits.get(1).getEstimatedProcessingTime(),
                "Second visit should be 2 hours");
        assertEquals(Duration.ofHours(5), scheduledVisits.get(2).getEstimatedProcessingTime(),
                "Third visit should be 5 hours");
        assertEquals(Duration.ofHours(10), scheduledVisits.get(3).getEstimatedProcessingTime(),
                "Fourth visit should be 10 hours");
    }

    @Test
    public void testGetName() {
        ShortestJobFirstStrategy strategy = new ShortestJobFirstStrategy();
        assertEquals("SJF", strategy.getName(), "Strategy name should be 'SJF'");
    }
}