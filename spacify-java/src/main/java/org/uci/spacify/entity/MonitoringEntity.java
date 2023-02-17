package org.uci.spacify.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="monitoring", schema="corespacify")
public class MonitoringEntity {
    @Id
    @Column(name = "monitoring_id")
    private Long monitoring_id;

    @Column(name="tippers_space_id")
    @NotNull
    private Long tippersSpaceId;

    @Column(name="mac_address")
    @NotNull
    private String macAddress;

    @Column(name="occupancy")
    @NotNull
    private Integer roomOccupancy;

    @Column(name="timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date timestamp;

    public MonitoringEntity() {
    }

    public MonitoringEntity(Long monitoring_id, Long tippersSpaceId, String macAddress, Integer roomOccupancy, Date timestamp) {
        this.monitoring_id = monitoring_id;
        this.tippersSpaceId = tippersSpaceId;
        this.macAddress = macAddress;
        this.roomOccupancy = roomOccupancy;
        this.timestamp = timestamp;
    }
}
