package org.uci.spacifyEngine.services;

import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.MacAddressEntity;
import org.uci.spacifyLib.entity.MonitoringEntity;
import org.uci.spacifyLib.entity.ReservationEntity;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyLib.repsitory.MacAddressRepository;
import org.uci.spacifyLib.repsitory.MonitoringRepository;
import org.uci.spacifyLib.repsitory.RoomRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MonitoringService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MonitoringRepository monitoringRepository;

    @Autowired
    private MacAddressRepository macAddressRepository;

    private List<MonitoringEntity> getAllMonitoringEntitiesInTimeBound(RoomEntity roomEntity, ReservationEntity reservationEntity) {
        int tippersSpaceId = roomEntity.getTippersSpaceId();
        List<MacAddressEntity> macAddressEntityList = this.macAddressRepository.findAllByMacAddressPK_UserId(reservationEntity.getUser_id());
        if (macAddressEntityList.isEmpty())
            return null;

        List<String> macAddressList = macAddressEntityList.stream().map(macAddressEntity -> macAddressEntity.getMacAddressPK().getMacAddress()).toList();
        List<MonitoringEntity> monitoringEntityList = this.monitoringRepository.findAllByTimestampFromAfterAndTippersSpaceIdAndMacAddressIn(reservationEntity.getTimeFrom(), tippersSpaceId, macAddressList);

        if (monitoringEntityList.isEmpty())
            return null;
        return monitoringEntityList;
    }

    public Pair<LocalDateTime,LocalDateTime> getActualTimeBounds(ReservationEntity reservationEntity) {
        Optional<RoomEntity> roomEntity = this.roomRepository.findById(reservationEntity.getRoomId());
        if (roomEntity.isPresent()) {
            List<MonitoringEntity> monitoringEntityList = this.getAllMonitoringEntitiesInTimeBound(roomEntity.get(), reservationEntity);
            if (Objects.isNull(monitoringEntityList))
                return null;

            monitoringEntityList.sort(new Comparator<MonitoringEntity>() {
                @Override
                public int compare(MonitoringEntity a, MonitoringEntity b) {
                    return a.getTimestampFrom().compareTo(b.getTimestampFrom());
                }
            });

            LocalDateTime actualTimeFrom = monitoringEntityList.get(0).getTimestampFrom();
            LocalDateTime actualTimeTo = monitoringEntityList.get(monitoringEntityList.size()-1).getTimestampTo();

            return new Pair<LocalDateTime, LocalDateTime>(actualTimeFrom, actualTimeTo);
        }
        return null;
    }

    public int getActualRoomOccupancy(ReservationEntity reservationEntity) {
        Optional<RoomEntity> roomEntity = this.roomRepository.findById(reservationEntity.getRoomId());
        if (roomEntity.isPresent()) {
            List<MonitoringEntity> monitoringEntityList = this.getAllMonitoringEntitiesInTimeBound(roomEntity.get(), reservationEntity);
            if (Objects.isNull(monitoringEntityList) || monitoringEntityList.isEmpty())
                return 0;

            long sum = 0;
            for(MonitoringEntity monitoringEntity: monitoringEntityList) {
                sum += monitoringEntity.getRoomOccupancy();
            }
            return (int) (sum/monitoringEntityList.size());
        }
        return 0;
    }
}
