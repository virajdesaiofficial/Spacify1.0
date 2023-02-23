package org.uci.spacify.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static org.uci.spacify.utilities.Constants.SCHEMA_NAME;

@Getter
@Setter
@Entity
@Table(name="reservation", schema=SCHEMA_NAME)
public class ReservationEntity {
    @Id
    @Column(name = "reservation_id")
    private Long reservation_id;

    @Column(name = "guests")
    private Integer guests;

    @Column(name="time_from")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeFrom;

    @Column(name="time_to")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeTo;

    @Column(name="reserved_by")
    @NotNull
    private String user_id;

    @Column(name = "reserved_room")
    @NotNull
    private Long roomId;

    public ReservationEntity() {
    }
}
