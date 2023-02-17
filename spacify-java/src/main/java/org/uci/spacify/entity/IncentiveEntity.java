package org.uci.spacify.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="incentive", schema = "corespacify")
public class IncentiveEntity {
    @Id
    @Column(name = "incentive_id")
    private Long incentiveId;

    @Column(name="incentive_points")
    @NotNull
    private Long incentivePoints;

    @Column(name="timestamp")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name="user_id")
    @NotNull
    private UserEntity user;

    public IncentiveEntity() {
    }
}
