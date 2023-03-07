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
@Table(name = "reservation", schema = SCHEMA_NAME)
public class ReservationEntity {
    @Id
    @Column(name = "reservation_id")
    private Long reservation_id;

    @Column(name = "guests")
    private Integer guests;

    @Column(name = "time_from")
    @NotNull
    private LocalDateTime timeFrom;

    @Column(name = "time_to")
    @NotNull
    private LocalDateTime timeTo;

    @Column(name = "reserved_by")
    @NotNull
    private String user_id;

    @Column(name = "fk_room_id")
    @NotNull
    private Long roomId;

    public ReservationEntity() {
    }
}
