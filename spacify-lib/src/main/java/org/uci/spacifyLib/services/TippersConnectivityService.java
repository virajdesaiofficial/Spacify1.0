package org.uci.spacifyLib.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.dto.MacAdressesOfARoom;
import org.uci.spacifyLib.dto.OccupancyOfaRoom;
import org.uci.spacifyLib.dto.RoomDetail;
import org.uci.spacifyLib.entity.MonitoringEntity;
import org.uci.spacifyLib.entity.TippersDbSpaceEntity;
import org.uci.spacifyLib.repository.MonitoringRepository;
import org.uci.spacifyLib.repository.TippersDbSpacesRepository;
import org.slf4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TippersConnectivityService {

    @Value("${tippersdb.api.url}")
    private String tippersDbUrl;

    @Value("${hit.tippers:True}")
    private boolean hitTippers;

    private Gson gson = new Gson();

    @Autowired
    private MonitoringRepository monitoringRepository;

    private final String mockedMacAddressData = "[{\"spaceId\":1606,\"macAddress\":\"189\",\"startTime\":\"2023-03-06 21:04:16.888\",\"endTime\":\"2023-03-06 21:09:16.978\"},{\"spaceId\":1606,\"macAddress\":\"161\",\"startTime\":\"2023-03-06 21:09:16.978\",\"endTime\":\"2023-03-06 21:14:17.316\"},{\"spaceId\":1606,\"macAddress\":\"177\",\"startTime\":\"2023-03-06 21:14:17.316\",\"endTime\":\"2023-03-06 21:19:17.621\"},{\"spaceId\":1606,\"macAddress\":\"154\",\"startTime\":\"2023-03-06 21:19:17.621\",\"endTime\":\"2023-03-06 21:24:17.671\"}]";

    private final String mockedRoomList = "[0,[[1607,\"floor1\",  \"2023-03-06 21:04:16.888\", \"2023-03-06 21:09:16.978\",189]]]";
    @Autowired
    private TippersDbSpacesRepository tippersDbSpacesRepository;

    private static final Logger LOG = LoggerFactory.getLogger(TippersConnectivityService.class);

    public List<RoomDetail> getListOfBuildings() {

        List<TippersDbSpaceEntity> entityList = tippersDbSpacesRepository.findDistinctByspaceType("building");
        LOG.info("Query for buildings ran successfully returning data back");
        return entityList.stream().map(t -> {
            RoomDetail r = new RoomDetail();
            r.setRoomId(Long.valueOf(t.getSpaceId()));
            r.setRoomDescription(t.getSpaceName());
            return r;
        }).collect(Collectors.toList());
    }

    public List<RoomDetail> getSpaceIdAndRoomName(Integer buildingId) {

        List<TippersDbSpaceEntity> entityList = tippersDbSpacesRepository.findByBuildingIdAndSpaceTypeNotIn(buildingId, Arrays.asList("building", "floor"));
        LOG.info("Query for spaceId and roomName ran successfully returning data back");
        return entityList.stream().map(t -> {
            RoomDetail r = new RoomDetail();
            r.setRoomId(Long.valueOf(t.getSpaceId()));
            r.setRoomDescription(t.getSpaceName());
            return r;
        }).collect(Collectors.toList());

    }

    public void getOccupancyStatusForSpaceId(String spaceId, String startTime, String endTime) {

        if (hitTippers) {

            String url = tippersDbUrl + "/room_occupancy";
            try {

                Map<String, String> bodyMap = new HashMap<>();
                bodyMap.put("space_id", spaceId);
                bodyMap.put("start_time", startTime);
                bodyMap.put("end_time", endTime);
                String body = new Gson().toJson(bodyMap);
                HttpResponse<String> response = sendRequestToTippers(url, body);
                if (response == null || response.statusCode() == HttpStatus.OK.value() || response.statusCode() != HttpStatus.OK.value()) {

                    List<MonitoringEntity> occupancyOfaRoomList = new ArrayList<>();
                    JsonArray responseArray;
                    if (response != null && response.statusCode() == HttpStatus.OK.value()) {
                        responseArray = new Gson().fromJson(response.body(), JsonArray.class);
                    } else {
                        responseArray =  new Gson().fromJson(mockedRoomList, JsonArray.class);;
                    }

                    if (responseArray.get(0).getAsInt() == 0) {

                        for (JsonElement element : responseArray.get(1).getAsJsonArray()) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                            JsonArray arr = element.getAsJsonArray();

                            MonitoringEntity entity = new MonitoringEntity();
                            entity.setTippersSpaceId(arr.get(0).getAsInt());
                            entity.setRoomOccupancy(arr.get(4).getAsInt());
                            entity.setMonitoring_id(-1L);
                            entity.setTimestampTo(LocalDateTime.parse(arr.get(3).getAsString(), formatter));
                            entity.setTimestampFrom(LocalDateTime.parse(arr.get(2).getAsString(), formatter));
                            occupancyOfaRoomList.add(entity);


                        }
                        monitoringRepository.saveAll(occupancyOfaRoomList);
                        LOG.info("Parsing request successful. Sending back the data");

                    }
                } else {
                    LOG.error("The http request has returned status {}", response.statusCode());
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }

    }

    private List<OccupancyOfaRoom> getMockedOccupancyData() {
        return gson.fromJson(mockedRoomList, new ArrayList<OccupancyOfaRoom>().getClass());
    }

    public Optional<List<MacAdressesOfARoom>> getMacAddressesForSpaceId(String spaceId, String startTime, String endTime) {

        if (hitTippers) {

            String url = tippersDbUrl + "/macAddress";
            try {

                Map<String, String> bodyMap = new HashMap<>();
                bodyMap.put("space_id", spaceId);
                bodyMap.put("start_time", startTime);
                bodyMap.put("end_time", endTime);
                String body = new Gson().toJson(bodyMap);
                HttpResponse<String> response = sendRequestToTippers(url, body);

                if (response.statusCode() == HttpStatus.OK.value()) {

                    List<MacAdressesOfARoom> macAdressesOfARoomList = new ArrayList<>();
                    JsonArray responseArray = new Gson().fromJson(response.body(), JsonArray.class);

                    if (responseArray.get(0).getAsInt() == 0) {

                        for (JsonElement element : responseArray.get(1).getAsJsonArray()) {

                            //todo

                        }
                        LOG.info("Parsing request successful. Sending back the data");
                        return Optional.of(macAdressesOfARoomList);
                    } else {

                        LOG.error("The query did not run properly");
                        return Optional.empty();
                    }
                } else {

                    LOG.error("The http request has returned status {}", response.statusCode());
                    return Optional.empty();
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                return Optional.empty();
            }
        } else {

            List<MacAdressesOfARoom> data = gson.fromJson(mockedMacAddressData, new ArrayList<MacAdressesOfARoom>().getClass());
            return Optional.of(data);
        }

    }


    private HttpResponse<String> sendRequestToTippers(String url, String body){

        try {
            LOG.info("sending request to url : {}", url);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        }catch(Exception e){
            return null;
        }

    }
}
