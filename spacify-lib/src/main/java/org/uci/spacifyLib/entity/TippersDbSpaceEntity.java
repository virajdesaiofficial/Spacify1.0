package org.uci.spacifyLib.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static org.uci.spacifyLib.utilities.Constants.SCHEMA_NAME;

@Getter
@Setter
@Entity
@Table(name = "tippersdb_spaces", schema =  SCHEMA_NAME)
public class TippersDbSpaceEntity {

    @Column(name = "space_name")
    private String spaceName;

    @Id
    @Column(name = "space_id")
    private Integer spaceId;

    @Column(name = "space_type")
    private String spaceType;

    @Column(name = "building_id")
    private Integer buildingId;

    @Column(name = "floor_id")
    @Nullable
    private Integer floorId;

    @Column(name = "capacity")
    @Nullable
    private Integer capacity;

}
