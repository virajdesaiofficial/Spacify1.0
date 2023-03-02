package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyPortal.services.RoomService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/all")
    public List<RoomEntity> getAll() {
        return this.roomService.getAll();
    }
}
