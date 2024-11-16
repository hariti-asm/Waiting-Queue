package ma.hariti.asmaa.wrm;

import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.service.algorithm.ShortestJobFirstStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ShortestJobFirstStrategyTest {

    @Test
    public void testSchedule() {
        VisitId id1 = new VisitId();
        id1.setValue(1L);
        VisitId id2 = new VisitId();
        id2.setValue(2L);
        VisitId id3 = new VisitId();
        id3.setValue(3L);
        VisitId id4 = new VisitId();
        id4.setValue(4L);

        Visit visit1 = new Visit(id1, Duration.ofHours(5));
        Visit visit2 = new Visit(id2, Duration.ofHours(2));
        Visit visit3 = new Visit(id3, Duration.ofHours(10));
        Visit visit4 = new Visit(id4, Duration.ofHours(1));

        ShortestJobFirstStrategy strategy = new ShortestJobFirstStrategy();

        List<Visit> visits = Arrays.asList(visit1, visit2, visit3, visit4);

        List<Visit> scheduledVisits = strategy.schedule(visits);

        assertEquals(Duration.ofHours(1), scheduledVisits.get(0).getEstimatedProcessingTime());
        assertEquals(Duration.ofHours(2), scheduledVisits.get(1).getEstimatedProcessingTime());
        assertEquals(Duration.ofHours(5), scheduledVisits.get(2).getEstimatedProcessingTime());
        assertEquals(Duration.ofHours(10), scheduledVisits.get(3).getEstimatedProcessingTime());
    }

    @Test
    public void testGetName() {
        ShortestJobFirstStrategy strategy = new ShortestJobFirstStrategy();
        assertEquals(" SJF", strategy.getName());
    }
}
