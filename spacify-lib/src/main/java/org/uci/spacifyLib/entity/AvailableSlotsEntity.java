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
    private Long available_slots_id;

    @Column(name = "time_from")
    private Time time_from;

    @Column(name = "time_to")
    private Time time_to;

    @Column(name = "room_type")
    private String room_type;

    public AvailableSlotsEntity() {
    }
}
