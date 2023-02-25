package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyPortal.entity.RoomEntity;
import org.uci.spacifyPortal.repsitory.RoomRepository;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<RoomEntity> getAll() {
        return this.roomRepository.findAll();
    }
}
