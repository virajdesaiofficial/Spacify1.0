package org.uci.spacifyLib.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static org.uci.spacifyLib.utilities.Constants.SCHEMA_NAME;

@Entity
@Getter
@Setter
@Table(name = "incentive", schema = SCHEMA_NAME)
public class IncentiveEntity {
    @Id
    @Column(name = "incentive_id")
    private Long incentiveId;

    @Column(name = "incentive_points")
    @NotNull
    private Long incentivePoints;

    @Column(name = "timestamp")
    @NotNull
    private LocalDateTime timestamp;

    @Column(name="user_id")
    @NotNull
    private String userId;

    public IncentiveEntity() {
    }

    public IncentiveEntity(Long incentiveId, Long incentivePoints, LocalDateTime timestamp, String userId) {
        this.incentiveId = incentiveId;
        this.incentivePoints = incentivePoints;
        this.timestamp = timestamp;
        this.userId = userId;
    }
}
