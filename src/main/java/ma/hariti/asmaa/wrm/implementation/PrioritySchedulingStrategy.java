package ma.hariti.asmaa.wrm.implementation;

import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.service.SchedulingStrategy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PrioritySchedulingStrategy  implements SchedulingStrategy {
    private final List<String> priorityFactors;

    public PrioritySchedulingStrategy(List<String> priorityFactors) {
        this.priorityFactors = priorityFactors;
    }

    @Override
    public List<Visit> schedule(List<Visit> visits) {
        return visits.stream().peek(this::calculatePiorityScore).sorted(Comparator.comparing(Visit::getPriority).reversed()).collect(Collectors.toList());
    }
 private void calculatePiorityScore(Visit visit) {
        int score = 0;
        for (String priorityFactor : priorityFactors) {
            switch (priorityFactor) {
                case "urgency":
                    score += visit.getPriority()*10;
                    break;
                case "waiting-time":
                    Duration waitingTime = Duration.between(visit.getArrivalTime() , LocalDateTime.now());
                    score += (int) (waitingTime.toHours() * 2);
                    break;
                case "severity":
                    score += visit.getPriority() * 8;
                    break;


            }
            visit.setPriority((byte) score);
        }
 }
    @Override
    public String getName() {
        return "";
    }
}
