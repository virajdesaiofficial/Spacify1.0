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

@Getter
@Setter
@Entity
@Table(name = "monitoring", schema = SCHEMA_NAME)
public class MonitoringEntity {
    @Id
    @Column(name = "monitoring_id")
    private Long monitoring_id;

    @Column(name = "tippers_space_id")
    @NotNull
    private Integer tippersSpaceId;

    @Column(name = "mac_address")
    @NotNull
    private String macAddress;

    @Column(name = "occupancy")
    @NotNull
    private Integer roomOccupancy;

    @Column(name = "timestamp")
    @NotNull
    private LocalDateTime timestamp;

    public MonitoringEntity() {
    }

    public MonitoringEntity(Long monitoring_id, Integer tippersSpaceId, String macAddress, Integer roomOccupancy, LocalDateTime timestamp) {
        this.monitoring_id = monitoring_id;
        this.tippersSpaceId = tippersSpaceId;
        this.macAddress = macAddress;
        this.roomOccupancy = roomOccupancy;
        this.timestamp = timestamp;
    }
}
