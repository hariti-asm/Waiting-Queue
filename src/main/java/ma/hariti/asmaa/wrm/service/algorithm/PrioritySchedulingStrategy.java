package ma.hariti.asmaa.wrm.service.algorithm;

import ma.hariti.asmaa.wrm.entity.Visit;
import ma.hariti.asmaa.wrm.repository.SchedulingStrategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
public class PrioritySchedulingStrategy implements SchedulingStrategy {
    private final List<String> priorityFactors;
    private static final int WAITING_TIME_WEIGHT = 15;
    private static final int PRIORITY_WEIGHT = 8;
    private static final int PROCESSING_TIME_WEIGHT = 5;

    public PrioritySchedulingStrategy(List<String> priorityFactors) {
        this.priorityFactors = priorityFactors;
    }

    @Override
    public List<Visit> schedule(List<Visit> visits) {
        Map<Visit, Integer> rawScores = new HashMap<>();
        visits.forEach(visit -> rawScores.put(visit, calculateRawScore(visit)));

        int minScore = rawScores.values().stream().mapToInt(Integer::intValue).min().orElse(0);
        int maxScore = rawScores.values().stream().mapToInt(Integer::intValue).max().orElse(0);

        visits.forEach(visit -> {
            int rawScore = rawScores.get(visit);
            byte normalizedScore = (byte) (1 + ((rawScore - minScore) * 9) / Math.max(1, maxScore - minScore));
            visit.setPriority(normalizedScore);
        });

        return visits.stream()
                .sorted(Comparator.comparing(Visit::getPriority).reversed())
                .collect(Collectors.toList());
    }

    private int calculateRawScore(Visit visit) {
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

        return score;
    }

    private int calculateWaitingTimeScore(Visit visit) {
        Duration waitingTime = Duration.between(visit.getArrivalTime(), LocalDateTime.now());
        long hours = waitingTime.toHours();
        return (int) (Math.pow(hours + 1, 2) * WAITING_TIME_WEIGHT);
    }

    private int calculateUrgencyScore(Visit visit) {
        return (int) (Math.pow(visit.getPriority(), 2) * PRIORITY_WEIGHT);
    }

    private int calculateSeverityScore(Visit visit) {
        long processingMinutes = visit.getEstimatedProcessingTime().toMinutes();
        return (int) (processingMinutes * PROCESSING_TIME_WEIGHT);
    }

    @Override
    public String getName() {
        return "Priority";
    }
}