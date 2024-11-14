package ma.hariti.asmaa.wrm.repository;

import ma.hariti.asmaa.wrm.entity.Visit;

import java.util.List;

public interface SchedulingStrategy {
    List<Visit> schedule(List<Visit> visits) ;
    String getName();
}
