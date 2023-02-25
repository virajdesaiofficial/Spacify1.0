package org.uci.spacifyPortal.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.uci.spacifyPortal.utilities.Constants.SCHEMA_NAME;

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

    @ManyToOne
    @JoinColumn(name="user_id")
    @NotNull
    private String user_id;

    public IncentiveEntity() {
    }
}
