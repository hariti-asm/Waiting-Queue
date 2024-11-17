package ma.hariti.asmaa.wrm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.hariti.asmaa.wrm.embeddedable.VisitId;
import ma.hariti.asmaa.wrm.enumeration.Status;
import ma.hariti.asmaa.wrm.util.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "visits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Visit extends BaseEntity {

    @EmbeddedId
    private VisitId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("visitorId")
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("waitingListId")
    @JoinColumn(name = "waiting_list_id", nullable = false)
    private WaitingList waitingList;

    @Column(nullable = false)
    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Byte priority;
    @Column(nullable = false)
    private Duration estimatedProcessingTime;

    public Visit(VisitId id, Duration estimatedProcessingTime) {
        this.id = id;
        this.estimatedProcessingTime = estimatedProcessingTime;
    }

    public Visit(VisitId id, LocalDateTime arrivalTime, LocalTime startTime, LocalTime endTime,
                 Status status, byte priority, Duration estimatedProcessingTime) {
    }

    @PrePersist
    protected void onCreate() {
        if (id == null && visitor != null && waitingList != null) {
            id = new VisitId(visitor.getId(), waitingList.getId());
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (id == null && visitor != null && waitingList != null) {
            id = new VisitId(visitor.getId(), waitingList.getId());
        }
    }

}
