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
import org.uci.spacifyPortal.utilities.ReservationSlot;
import org.uci.spacifyPortal.utilities.TimeBound;
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

    public List<ReservationSlot> getAllAvailableSlots(String room_type) {
//        String room_type = "STUDY";                                                     //to take from UI
        LocalDateTime from_date_time = LocalDateTime.parse("2023-02-22T13:00:00");     //to take from UI
        LocalDateTime to_date_time = LocalDateTime.parse("2023-02-22T15:00:00");       //to take from UI


        List<AvailableSlotsEntity> AvailableSlotsEntityList;
        AvailableSlotsEntityList = (List<AvailableSlotsEntity>) availableSlotsRepository.findByroomType(RoomType.valueOf(room_type));

        LocalDateTime availableSlotsTimeFrom = LocalDateTime.parse(from_date_time.toLocalDate() + "T" + AvailableSlotsEntityList.get(0).getTimeFrom());
        LocalDateTime availableSlotsTimeTo = LocalDateTime.parse(to_date_time.toLocalDate() + "T" + AvailableSlotsEntityList.get(0).getTimeTo());
        List<RoomEntity> RoomEntityList;
        RoomEntityList = roomRepository.findByroomType(RoomType.STUDY);                     //HARDCODE VALUE OF ROOM TYPE
        List<Long> room_ids = new ArrayList<>();

        for(RoomEntity roomEntity : RoomEntityList) {                                       //get room ids from the room table for room type "study"
            room_ids.add(roomEntity.getRoomId());
        }

        List<ReservationEntity> arr = reservationRepository.findByroomIdIn(room_ids);       //find room ids in the reservation table to get time_to, etc
        List<LocalDateTime> time_to = new ArrayList<>();
        List<LocalDateTime> time_from = new ArrayList<>();


        for(int i=0; i<arr.size(); i++) {
            time_from.add(arr.get(i).getTimeFrom());
            time_to.add(arr.get(i).getTimeTo());
        }

        LocalDateTime slot_date_start = LocalDateTime.parse(from_date_time.toString().split("T")[0] + "T00:00:00");             //splitting date time into date
        LocalDateTime slot_date_end  = LocalDateTime.parse(from_date_time.toString().split("T")[0] + "T23:59:00");
        List<ReservationEntity> booked_slot_time = new ArrayList<>();

        booked_slot_time =  reservationRepository.findBytimeFromBetweenAndRoomIdIn(slot_date_start, slot_date_end, room_ids);

        Map<Long, TimeReserved>  slots_reserved = new HashMap<>();

        for(int i=0; i<booked_slot_time.size(); i++) {
            slots_reserved.put(booked_slot_time.get(i).getRoomId(), new TimeReserved(booked_slot_time.get(i).getTimeFrom(), booked_slot_time.get(i).getTimeTo()));
        }

        Map<Long, ArrayList<ArrayList<LocalDateTime> >> slots_available = new HashMap<>();

        for(Map.Entry<Long, TimeReserved> entry : slots_reserved.entrySet()) {
            LocalDateTime end_time = (entry.getValue().fromTime);

            ArrayList<ArrayList<LocalDateTime> > all_slots = new ArrayList<>();
            LocalDateTime i = availableSlotsTimeFrom;
            while(i.isBefore(end_time) || i.isEqual(end_time)) {

                ArrayList<LocalDateTime> one_slot = new ArrayList<>();
                one_slot.add(0, i);
                i = i.plusHours(1);
                one_slot.add(1, i);
                all_slots.add(new ArrayList<LocalDateTime>(one_slot));
            }
            slots_available.put(entry.getKey(), new ArrayList<>(all_slots));
        }

        for(Map.Entry<Long, TimeReserved> entry : slots_reserved.entrySet()) {
            LocalDateTime start_time = (entry.getValue().toTime);
            ArrayList<ArrayList<LocalDateTime> > all_slots = new ArrayList<>();
            LocalDateTime i = start_time;
            while((i.isAfter(start_time) || i.isEqual(start_time)) && i.isBefore(availableSlotsTimeTo)) {

                System.out.println("slots times are " + i);
                ArrayList<LocalDateTime> one_slot = new ArrayList<>();
                one_slot.add(0, i);
                i = i.plusHours(1);
                one_slot.add(1, i);
                all_slots.add(new ArrayList<LocalDateTime>(one_slot));
            }
            slots_available.get(entry.getKey()).addAll(new ArrayList<>(all_slots));
        }

        System.out.println(slots_available);

        List<ReservationSlot> result = new ArrayList<>();
        for (Map.Entry<Long, ArrayList<ArrayList<LocalDateTime>>> entry: slots_available.entrySet()) {
            List<TimeBound> timeBounds = new ArrayList<>();
            entry.getValue().stream().forEach(e-> {
                timeBounds.add(new TimeBound(e.get(0), e.get(1)));
            });
            result.add(new ReservationSlot(entry.getKey(), new ArrayList<TimeBound>(timeBounds)));
        }
        return result;
    }

    class TimeReserved {
        LocalDateTime fromTime;
        LocalDateTime toTime;

        public TimeReserved(LocalDateTime fromTime, LocalDateTime toTime) {
            this.fromTime = fromTime;
            this.toTime = toTime;
        }




    }

}

