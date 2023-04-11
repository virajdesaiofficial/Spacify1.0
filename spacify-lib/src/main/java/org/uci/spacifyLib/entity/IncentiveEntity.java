package org.uci.spacifyLib.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.uci.spacifyLib.utilities.Constants.SCHEMA_NAME;

@Entity
@Getter
@Setter
@Table(name = "incentive", schema = SCHEMA_NAME)
public class IncentiveEntity {
    @Id
    @Column(name = "incentive_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incentiveId;

    @Column(name = "")
    @NotNull
    private Long incentivePoints;

    @Column(name = "timestamp")
    @NotNull
    private LocalDateTime timestamp;

    @Column(name="user_id")
    @NotNull
    private String userId;

    @Column(name = "added")
    @NotNull
    private boolean added;

    public IncentiveEntity() {
    }

    public IncentiveEntity(Long incentivePoints, LocalDateTime timestamp, String userId, boolean added) {
        this.incentivePoints = incentivePoints;
        this.timestamp = timestamp;
        this.userId = userId;
        this.added = added;
    }
}
