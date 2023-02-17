package org.uci.spacify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacify.entity.RoomEntity;
import org.uci.spacify.repsitory.RoomRepository;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<RoomEntity> getAll() {
        return this.roomRepository.findAll();
    }
}
