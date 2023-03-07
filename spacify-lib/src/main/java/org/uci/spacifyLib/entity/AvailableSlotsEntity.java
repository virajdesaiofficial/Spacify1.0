package org.uci.spacifyLib.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Time;

import static org.uci.spacifyLib.utilities.Constants.SCHEMA_NAME;

@Getter
@Setter
@Entity
@Table(name = "available_slots", schema = SCHEMA_NAME)
public class AvailableSlotsEntity {
    @Id
    @NotNull
    @Column(name = "available_slots_id")
    private Long availableSlotsId;

    @Column(name = "time_from")
    private Time timeFrom;

    @Column(name = "time_to")
    private Time timeTo;

    @Column(name = "room_type")
    private String roomType;

    public AvailableSlotsEntity() {
    }
}
