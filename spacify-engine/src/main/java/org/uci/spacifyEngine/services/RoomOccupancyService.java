package org.uci.spacifyEngine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.MonitoringEntity;
import org.uci.spacifyLib.entity.SubscriberEntity;
import org.uci.spacifyLib.repository.MonitoringRepository;
import org.uci.spacifyLib.repository.RoomRepository;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyLib.repository.SubscriberRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class RoomOccupancyService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MonitoringRepository monitoringRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    public List<RoomEntity> getRoomsWithZeroOccupancy() {
        LocalDateTime current_time = LocalDateTime.parse("2023-02-22T12:00:00"); // HARDCODED

        List<RoomEntity> roomIdsWithZeroOccupancy = new ArrayList<>();

        // Iterate over rooms to check occupancy
        List<SubscriberEntity> rooms = subscriberRepository.findAllBySubscribed(true);
        for (SubscriberEntity room : rooms) {
            Long spacifyRoomId = room.getUserRoomPK().getRoomId();
            RoomEntity roomEntity = roomRepository.findByRoomId(spacifyRoomId);
            int tippers_room_id = roomEntity.getTippersSpaceId();
            List<MonitoringEntity> monitoringObjects = monitoringRepository.findAllBytippersSpaceId(tippers_room_id);
            List<LocalDateTime> timestampToValues = new ArrayList<>(); // timestamp_to values for the given room ID

            LocalDateTime fifteenMinutesBeforeCurrentTime = current_time.minusMinutes(15);

            for (MonitoringEntity monitoringObject : monitoringObjects) {
                LocalDateTime timestampTo = monitoringObject.getTimestampTo();
                if (timestampTo.isEqual(current_time) || (timestampTo.isAfter(fifteenMinutesBeforeCurrentTime) && timestampTo.isBefore(current_time))) {
                    timestampToValues.add(timestampTo);
                }
            }

            // Check if the occupancy is zero for timestampToValues
            boolean hasZeroOccupancy = false;
            for (LocalDateTime timestampToValue : timestampToValues) {
                List<MonitoringEntity> monitoringList = monitoringRepository.findByTippersSpaceIdAndTimestampTo(tippers_room_id, timestampToValue);
                for (MonitoringEntity monitoring : monitoringList) {
                    if (monitoring.getRoomOccupancy() != 0) {
                        hasZeroOccupancy = false;
                        break;
                    }
                }
            }

            if (hasZeroOccupancy) {
                roomIdsWithZeroOccupancy.add(roomEntity);
            }

            // Debug statements
//            System.out.println("Room ID: " + room.getRoomId());
//            System.out.println("Has Zero Occupancy: " + hasZeroOccupancy);
//            System.out.println("Room IDs with Zero Occupancy: " + roomIdsWithZeroOccupancy);
//            System.out.println("TimestampTo Values: " + timestampToValues);
        }

        return roomIdsWithZeroOccupancy;
    }


}
