package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.OwnerEntity;
import org.uci.spacifyLib.entity.UserRoomPK;
import org.uci.spacifyLib.repsitory.OwnerRepository;

import java.util.List;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    public void createOwner(String userId, Long roomId) {
        UserRoomPK userRoomPK = new UserRoomPK(userId, roomId);
        OwnerEntity ownerEntity = new OwnerEntity(userRoomPK);
        this.ownerRepository.save(ownerEntity);
    }

    public List<OwnerEntity> getAllOwnedRooms(String userId) {
        return this.ownerRepository.findAllByUserRoomPK_UserId(userId);
    }

}
