package org.uci.spacify.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="reservation", schema="corespacify")
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

    @ManyToOne
    @JoinColumn(name="reserved_by")
    @NotNull
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "reserved_room")
    @NotNull
    private RoomEntity roomEntity;

    public ReservationEntity() {
    }
}
