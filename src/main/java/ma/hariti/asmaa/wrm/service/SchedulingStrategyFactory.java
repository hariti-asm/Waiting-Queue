package ma.hariti.asmaa.wrm.service;

import ma.hariti.asmaa.wrm.config.SchedulingAlgorithmsConfig;
import ma.hariti.asmaa.wrm.repository.SchedulingStrategy;
import ma.hariti.asmaa.wrm.service.algorithm.FifoSchedulingStrategy;
import ma.hariti.asmaa.wrm.service.algorithm.PrioritySchedulingStrategy;
import ma.hariti.asmaa.wrm.service.algorithm.ShortestJobFirstStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SchedulingStrategyFactory {
    private final SchedulingAlgorithmsConfig algorithmsConfig;
    private final Map<String, SchedulingStrategy> strategies;

    public SchedulingStrategyFactory(SchedulingAlgorithmsConfig algorithmsConfig) {
        this.algorithmsConfig = algorithmsConfig;
        this.strategies = new HashMap<>();
        initializeStrategies();
    }

    private void initializeStrategies() {
        // Initialiser les stratégies basées sur la configuration
        if (algorithmsConfig.getFifo() != null) {
            strategies.put(algorithmsConfig.getFifo().getName(), new FifoSchedulingStrategy());
        }
        if (algorithmsConfig.getPriority() != null) {
            strategies.put(algorithmsConfig.getPriority().getName(),
                    new PrioritySchedulingStrategy(algorithmsConfig.getPriority().getPriorityFactors()));
        }
        if (algorithmsConfig.getShortestJobFirst() != null) {
            strategies.put(algorithmsConfig.getShortestJobFirst().getName(),
                    new ShortestJobFirstStrategy());
        }
    }

    public SchedulingStrategy getStrategy(String strategyName) {
        SchedulingStrategy strategy = strategies.get(strategyName);
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown strategy: " + strategyName);
        }
        return strategy;
    }
}
