package org.uci.spacifyPortal.utilities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
public class TipperSpace {
    private String name;

    private int tippersSpaceId;

    public TipperSpace() {
    }

    public TipperSpace(String name, int tippersSpaceId) {
        this.name = name;
        this.tippersSpaceId = tippersSpaceId;
    }
}
