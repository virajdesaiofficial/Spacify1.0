package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.MonitoringEntity;
import org.uci.spacifyLib.repository.MonitoringRepository;
import org.uci.spacifyLib.repository.RoomRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class OccupancyService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MonitoringRepository monitoringRepository;

    public List<Long> getRoomsWithZeroOccupancy(List<Long> roomIds) {

        LocalDateTime current_time = LocalDateTime.parse("2023-02-22T13:15:00"); // HARDCODED

        List<Long> roomIdsWithZeroOccupancy = new ArrayList<>();

        for (Long roomId : roomIds) {
            Integer tippers_room_id = roomRepository.findByRoomId(roomId).getTippersSpaceId();
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
            boolean hasZeroOccupancy = true;
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
                roomIdsWithZeroOccupancy.add(roomId);
            }
        }

        return roomIdsWithZeroOccupancy;
    }
}
