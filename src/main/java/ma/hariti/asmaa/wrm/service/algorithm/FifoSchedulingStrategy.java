package ma.hariti.asmaa.wrm.service.algorithm;

import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.repository.SchedulingStrategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FifoSchedulingStrategy  implements SchedulingStrategy {

    @Override
    public List<Visit> schedule(List<Visit> visits) {
        return visits.stream()
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
