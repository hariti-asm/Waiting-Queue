package ma.hariti.asmaa.wrm.embeddedable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitId implements Serializable {

    @Column(name = "visitor_id")
    private Long visitorId;

    @Column(name = "waiting_list_id")
    private Long waitingListId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisitId visitId = (VisitId) o;
        return Objects.equals(visitorId, visitId.visitorId) &&
                Objects.equals(waitingListId, visitId.waitingListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitorId, waitingListId);
    }
}
