package ma.hariti.asmaa.wrm.service.algorithm;

import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.repository.SchedulingStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FifoSchedulingStrategy implements SchedulingStrategy {

    @Override
    public List<Visit> schedule(List<Visit> visits) {
        // Check if input list is null or empty
        if (visits == null || visits.isEmpty()) {
            return new ArrayList<>();
        }

        // Filter out any null visits and sort by arrival time
        return visits.stream()
                .filter(Objects::nonNull)  // Filter out null visits
                .filter(visit -> visit.getArrivalTime() != null)  // Filter out visits with null arrival time
                .sorted(Comparator.comparing(
                        Visit::getArrivalTime,
                        Comparator.nullsFirst(Comparator.naturalOrder())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return "First in First Out";
    }
}