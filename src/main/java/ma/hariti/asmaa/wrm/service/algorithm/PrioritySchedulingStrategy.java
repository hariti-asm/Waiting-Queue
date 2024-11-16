package ma.hariti.asmaa.wrm.service.algorithm;

import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.repository.SchedulingStrategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PrioritySchedulingStrategy implements SchedulingStrategy {
    private final List<String> priorityFactors;
    private static final int URGENCY_WEIGHT = 10;
    private static final int SEVERITY_WEIGHT = 8;
    private static final int WAITING_TIME_WEIGHT = 4;

    public PrioritySchedulingStrategy(List<String> priorityFactors) {
        this.priorityFactors = priorityFactors;
    }

    @Override
    public List<Visit> schedule(List<Visit> visits) {
        visits.forEach(visit -> visit.setPriority((byte) 0));

        return visits.stream()
                .peek(this::calculatePriorityScore)
                .sorted(Comparator.comparing(Visit::getPriority).reversed())
                .collect(Collectors.toList());
    }

    private void calculatePriorityScore(Visit visit) {
        int score = 0;
        System.out.println("Calculating score for visit " + visit.getId());

        for (String priorityFactor : priorityFactors) {
            int factorScore = 0;

            switch (priorityFactor) {
                case "waiting-time":
                    factorScore = calculateWaitingTimeScore(visit);
                    System.out.println("Waiting time score: " + factorScore);
                    break;
                case "urgency":
                    factorScore = calculateUrgencyScore(visit);
                    System.out.println("Urgency score: " + factorScore);
                    break;
                case "severity":
                    factorScore = calculateSeverityScore(visit);
                    System.out.println("Severity score: " + factorScore);
                    break;
            }

            score += factorScore;
            System.out.println("Running total: " + score);
        }

        score = Math.min(127, Math.max(-128, score));
        visit.setPriority((byte) score);
        System.out.println("Final score: " + score);
    }

    private int calculateWaitingTimeScore(Visit visit) {
        Duration waitingTime = Duration.between(visit.getArrivalTime(), LocalDateTime.now());
        return (int) (waitingTime.toHours() * WAITING_TIME_WEIGHT);
    }

    private int calculateUrgencyScore(Visit visit) {
        Duration timeUntilArrival = Duration.between(LocalDateTime.now(), visit.getArrivalTime());
        return (int) Math.max(0, timeUntilArrival.toMinutes()) * URGENCY_WEIGHT;
    }

    private int calculateSeverityScore(Visit visit) {
        long estimatedProcessingTimeMinutes = visit.getEstimatedProcessingTime().toMinutes();
        return (int) Math.max(0, estimatedProcessingTimeMinutes) * SEVERITY_WEIGHT;
    }

    @Override
    public String getName() {
        return "Priority";
    }
}
