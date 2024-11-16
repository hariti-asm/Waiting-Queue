package ma.hariti.asmaa.wrm.repository;

import ma.hariti.asmaa.wrm.entity.Visit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SchedulingStrategy {
    List<Visit> schedule(List<Visit> visits) ;
    String getName();
}
