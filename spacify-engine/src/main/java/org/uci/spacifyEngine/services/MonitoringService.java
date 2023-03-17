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
import java.util.Optional;

@Service
public class MonitoringService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MonitoringRepository monitoringRepository;

    @Autowired
    private MacAddressRepository macAddressRepository;

    public Pair<LocalDateTime,LocalDateTime> getActualTimeBounds(ReservationEntity reservationEntity) {
        Optional<RoomEntity> roomEntity = this.roomRepository.findById(reservationEntity.getRoomId());
        if (roomEntity.isPresent()) {
            int tippersSpaceId = roomEntity.get().getTippersSpaceId();
            List<MacAddressEntity> macAddressEntityList = this.macAddressRepository.findAllByMacAddressPK_UserId(reservationEntity.getUser_id());
            if (macAddressEntityList.isEmpty())
                return null;

            List<String> macAddressList = macAddressEntityList.stream().map(macAddressEntity -> macAddressEntity.getMacAddressPK().getMacAddress()).toList();
            List<MonitoringEntity> monitoringEntityList = this.monitoringRepository.findAllByTimestampFromAfterAndTippersSpaceIdAndMacAddressIn(reservationEntity.getTimeFrom(), tippersSpaceId, macAddressList);

            if (monitoringEntityList.isEmpty())
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
}
