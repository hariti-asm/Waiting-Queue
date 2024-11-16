package ma.hariti.asmaa.wrm.service;

import lombok.extern.slf4j.Slf4j;
import ma.hariti.asmaa.wrm.config.SchedulingAlgorithmsConfig;
import ma.hariti.asmaa.wrm.repository.SchedulingStrategy;
import ma.hariti.asmaa.wrm.service.algorithm.FifoSchedulingStrategy;
import ma.hariti.asmaa.wrm.service.algorithm.PrioritySchedulingStrategy;
import ma.hariti.asmaa.wrm.service.algorithm.ShortestJobFirstStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
@Slf4j
public class SchedulingStrategyFactory {
    private final SchedulingAlgorithmsConfig algorithmsConfig;
    private final Map<String, SchedulingStrategy> strategies;

    public SchedulingStrategyFactory(SchedulingAlgorithmsConfig algorithmsConfig) {
        this.algorithmsConfig = algorithmsConfig;
        this.strategies = new HashMap<>();
        initializeStrategies();
    }

    private void initializeStrategies() {
        log.info("Initializing scheduling strategies...");

        if (algorithmsConfig.getFifo() != null) {
            String fifoName = algorithmsConfig.getFifo().getName();
            log.info("Adding FIFO strategy with name: {}", fifoName);
            strategies.put(fifoName, new FifoSchedulingStrategy());
        } else {
            log.warn("FIFO configuration is null");
        }

        if (algorithmsConfig.getPriority() != null) {
            String priorityName = algorithmsConfig.getPriority().getName();
            log.info("Adding Priority strategy with name: {}", priorityName);
            strategies.put(priorityName,
                    new PrioritySchedulingStrategy(algorithmsConfig.getPriority().getPriorityFactors()));
        }

        if (algorithmsConfig.getShortestJobFirst() != null) {
            String sjfName = algorithmsConfig.getShortestJobFirst().getName();
            log.info("Adding SJF strategy with name: {}", sjfName);
            strategies.put(sjfName,
                    new ShortestJobFirstStrategy());
        }

        log.info("Available strategies: {}", strategies.keySet());
    }

    public SchedulingStrategy getStrategy(String strategyName) {
        log.info("Requesting strategy: {}", strategyName);
        log.info("Available strategies: {}", strategies.keySet());

        SchedulingStrategy strategy = strategies.get(strategyName);
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown strategy: " + strategyName
                    + ". Available strategies are: " + strategies.keySet());
        }
        return strategy;
    }
}