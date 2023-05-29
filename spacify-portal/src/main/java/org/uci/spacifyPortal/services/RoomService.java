package org.uci.spacifyPortal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.dto.RoomDetail;
import org.uci.spacifyLib.dto.Rule;
import org.uci.spacifyLib.entity.*;
import org.uci.spacifyLib.repository.AvailableSlotsRepository;
import org.uci.spacifyLib.repository.OwnerRepository;
import org.uci.spacifyLib.repository.RoomRepository;
import org.uci.spacifyLib.repository.SubscriberRepository;
import org.uci.spacifyLib.services.TippersConnectivityService;
import org.uci.spacifyLib.utilities.SpacifyUtility;
import org.uci.spacifyLib.dto.CreateRequest;
import org.uci.spacifyPortal.controllers.RoomController;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TippersConnectivityService tippersConnectivityService;

    @Autowired
    private AvailableSlotsRepository availableSlotsRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    private static final Logger LOG = LoggerFactory.getLogger(RoomService.class);

    public void createRoom(Long roomId, String owner, List<Rule> rules) throws JsonProcessingException {

        RoomEntity room = new RoomEntity();
        room.setRoomId(Long.valueOf(roomId));
        room.setRoomRules(SpacifyUtility.serializeListOfRules(rules));

        UserRoomPK userRoomPK = new UserRoomPK();
        userRoomPK.setRoomId(Long.valueOf(roomId));
        userRoomPK.setUserId(owner);
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setUserRoomPK(userRoomPK);

        List<OwnerEntity> owners = new ArrayList<>();
        owners.add(ownerEntity);

        roomRepository.save(room);
        ownerRepository.saveAll(owners);
    }

    public RoomEntity createRoom(CreateRequest createRequest) {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomRules(null);
        roomEntity.setTippersSpaceId(createRequest.getTippersSpaceId());
        // we set roomId to -1, as the database trigger: setDefault will generate the next ID and use that for the insert.
        roomEntity.setRoomId(-1L);
        roomEntity.setRoomType(RoomType.valueOf(createRequest.getRoomType()));
        roomEntity.setRoomName(createRequest.getRoomName());

        this.roomRepository.save(roomEntity);

        // we need to get the roomId of the room we just created, hence the find by tippers space id
        return this.roomRepository.findByTippersSpaceId(createRequest.getTippersSpaceId());
    }

    public List<RoomEntity> getAllRooms() {
        return this.roomRepository.findAll();
    }

    public boolean doesRoomExist(int tippersSpaceId) {
        return Objects.nonNull(this.roomRepository.findByTippersSpaceId(tippersSpaceId));
    }

    public boolean addRules(Long roomId, String owner, List<Rule> rules) throws JsonProcessingException {
        Optional<RoomEntity> room = this.roomRepository.findById(roomId);
        rules.forEach(rule -> rule.setFired(false));
        if (room.isPresent()) {
            room.get().setRoomRules(SpacifyUtility.serializeListOfRules(rules));
            this.roomRepository.save(room.get());
            return true;
        }
        return false;
    }

    public List<RoomDetail> getRoomsForSubscribtion(String buildingTipperSpaceId){
        List<RoomDetail> buildingRooms =  tippersConnectivityService.getSpaceIdAndRoomName(Integer.parseInt(buildingTipperSpaceId));
        List<Integer> roomTippersId = buildingRooms.stream().map(room -> room.getRoomId().intValue()).collect(Collectors.toList());
        List<RoomEntity> spacifyRooms = roomRepository.findByroomTypeAndTippersSpaceIdIn(RoomType.COMMON_SPACE, roomTippersId);

        LOG.info("Found spacify rooms available for subscribing and all tippers room. Finding for intersection now");

        List<RoomDetail> roomsForSubscribtion = spacifyRooms.stream().map( room -> {
                                                    RoomDetail r = new RoomDetail();
                                                    r.setRoomDescription(room.getRoomName());
                                                    r.setRoomId(room.getRoomId());
                                                    return r;
                                                }).collect(Collectors.toList());

        return roomsForSubscribtion;
    }

    public List<String> getRoomRules(String spacifyRoomId) throws IOException {

        PebbleEngine engine = new PebbleEngine.Builder().build();
        PebbleTemplate compiledTemplate = engine.getTemplate("rulesTemplate");

        RoomEntity room = roomRepository.findByRoomId(Long.parseLong(spacifyRoomId));
        List<Rule> roomRules = SpacifyUtility.deserializeListOfRules(room.getRoomRules());

        Map<String, Object> context = new HashMap<>();
        context.put("rules", roomRules);

        LOG.info("Pebble template read. Evaluating now");

        Writer writer = new StringWriter();
        compiledTemplate.evaluate(writer, context);

        String output = writer.toString();

        LOG.info("Pebble evaluation complete. Parsing result.");

        List<String> listOfRules = Arrays.stream(output.split("\\r\\n"))
                .map(String::trim).filter(str -> !str.isEmpty())
                .collect(Collectors.toList());

        return listOfRules;
    }

    public boolean subscribeToRoom(String userId, String spacifyRoomId){
        UserRoomPK userRoomPK = new UserRoomPK(userId, Long.parseLong(spacifyRoomId));
        Optional<SubscriberEntity> subscriberEntity = subscriberRepository.findAllByUserRoomPK(userRoomPK);
        if(subscriberEntity.isPresent()){
            LOG.info("User has already subscribed to the room");
            return false;
        }else{
            SubscriberEntity subscriber = new SubscriberEntity(userRoomPK, false);
            subscriberRepository.save(subscriber);
            LOG.info("User has successfully subscribed to the room");
            return true;
        }

    }

    public List<RoomEntity> getRoomsBasedOnIds(List<Long> roomIds) {
        return this.roomRepository.findByRoomIdIn(roomIds);
    }
}
