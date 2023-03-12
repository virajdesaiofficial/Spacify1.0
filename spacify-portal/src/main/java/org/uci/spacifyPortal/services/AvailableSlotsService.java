package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.AvailableSlotsEntity;
import org.uci.spacifyLib.entity.ReservationEntity;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyLib.entity.RoomType;
import org.uci.spacifyLib.repsitory.AvailableSlotsRepository;
import org.uci.spacifyLib.repsitory.ReservationRepository;
import org.uci.spacifyLib.repsitory.RoomRepository;

import java.math.BigInteger;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AvailableSlotsService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AvailableSlotsRepository availableSlotsRepository;

    public List<ReservationEntity> getAllAvailableSlots() {
        String room_type = "STUDY";                                                     //to take from UI
        LocalDateTime from_date_time = LocalDateTime.parse("2023-02-20T13:00");     //to take from UI
        LocalDateTime to_date_time = LocalDateTime.parse("2023-02-20T15:00");       //to take from UI

        List<BigInteger> room_ID = new ArrayList<>();
        List<AvailableSlotsEntity> AvailableSlotsEntityList;
        AvailableSlotsEntityList = (List<AvailableSlotsEntity>) availableSlotsRepository.findByroomType(room_type);

        Time available_slots_time_from = AvailableSlotsEntityList.get(0).getTimeFrom();
        Time available_slots_time_to = AvailableSlotsEntityList.get(0).getTimeTo();

        List<RoomEntity> RoomEntityList;
        RoomEntityList = roomRepository.findByroomType(RoomType.STUDY);                //HARDCODE VALUE OF ROOM TYPE
        List<Long> r_id = new ArrayList<>();


        for(RoomEntity roomEntity : RoomEntityList) {                                  //get room ids from the room table for room type "study"
            r_id.add(roomEntity.getRoomId());
        }

        List<ReservationEntity> ReservationEntityList;
        List<ReservationEntity> arr = reservationRepository.findByroomIdIn(r_id);       //find room ids in the reservation table to get time_to, etc
        List<LocalDateTime> time_to = new ArrayList<>();
        List<LocalDateTime> time_from = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();

        for(int i=0; i<arr.size(); i++) {
            time_from.add(arr.get(i).getTimeFrom());
            time_to.add(arr.get(i).getTimeTo());
        }

//        System.out.println("list is " + (reservationRepository.findByroomIdIn(r_id)));
//        for(int i = 0; i< r_id.size(); i++) {
//            System.out.println("list is " + (reservationRepository.findByroomId(r_id.get(i))).get(i).getTimeTo());
//        }
//        System.out.println("findby room id " + (reservationRepository.findByroomId(7L)).get(0));

//        for(int i=0; i<r_id.size(); i++) {
//            ReservationEntityList.add(reservationRepository.findByroomId(Collections.singletonList(r_id.get(i))));
//        }


//        for(ReservationEntity reservationEntity : ReservationEntityList) {
//            System.out.println("time from " + reservationEntity.getTimeFrom() + "time to " + reservationEntity.getTimeTo());
//        }

        return arr;
    }
}
