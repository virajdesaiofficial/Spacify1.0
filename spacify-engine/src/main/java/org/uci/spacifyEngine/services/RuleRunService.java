package org.uci.spacifyEngine.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.math3.util.Pair;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyEngine.calculators.DurationRuleCalculator;
import org.uci.spacifyEngine.calculators.OccupancyRuleCalculator;
import org.uci.spacifyEngine.calculators.RuleCalculator;
import org.uci.spacifyEngine.calculators.RuleIdEum;
import org.uci.spacifyLib.dto.Rule;
import org.uci.spacifyLib.entity.IncentiveEntity;
import org.uci.spacifyLib.entity.ReservationEntity;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyLib.entity.UserEntity;
import org.uci.spacifyLib.repsitory.IncentiveRepository;
import org.uci.spacifyLib.repsitory.RoomRepository;
import org.uci.spacifyLib.repsitory.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.uci.spacifyEngine.calculators.RuleIdEum.getRoomIdEnum;
import static org.uci.spacifyLib.utilities.SpacifyUtility.deserializeListOfRules;

@Service
public class RuleRunService {

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncentiveRepository incentiveRepository;

    @Autowired
    private MonitoringService monitoringService;

    private void calculateRule(Rule rule, ReservationEntity reservationEntity) {
        RuleIdEum ruleId = getRoomIdEnum(rule.getRuleId());
        RuleCalculator ruleCalculator = null;

        switch (ruleId) {
            case OCCUPANCY_RULE -> {
                ruleCalculator = new OccupancyRuleCalculator(reservationEntity, this.monitoringService);
            }
            case DURATION_RULE -> {
                ruleCalculator = new DurationRuleCalculator(reservationEntity, this.monitoringService);
            }
            default -> {
            }
        }

        if (Objects.isNull(ruleCalculator)) {
            System.out.println("No rule exists with the name: " + ruleId);
        }

        ruleCalculator.calculate(rule);
    }

    private Map<String, List<Pair<ReservationEntity, RoomEntity>>> groupByUser(Map<ReservationEntity, RoomEntity> reservationEntityRoomEntityMap) {
        Map<String, List<Pair<ReservationEntity, RoomEntity>>> userMap = new HashMap<String, List<Pair<ReservationEntity, RoomEntity>>>();

        for(Map.Entry<ReservationEntity, RoomEntity> entry: reservationEntityRoomEntityMap.entrySet()) {
            ReservationEntity reservationEntity = entry.getKey();
            RoomEntity roomEntity = entry.getValue();

            userMap.computeIfAbsent(reservationEntity.getUser_id(), e -> new ArrayList<>())
                    .add(new Pair<ReservationEntity, RoomEntity>(reservationEntity, roomEntity));
        }

        return userMap;
    }

    public Map<ReservationEntity, RoomEntity> getReservationToRoomMap(List<ReservationEntity> reservationEntityList) {
        List<Long> roomIds = reservationEntityList.stream().map(ReservationEntity::getRoomId).collect(Collectors.toList());

        List<RoomEntity> roomEntityList = this.roomRepository.findByRoomIdIn(roomIds);

        Map<ReservationEntity, RoomEntity> map = new HashMap<>();

        reservationEntityList.forEach(reservationEntity -> {
            Optional<RoomEntity> roomEntity = roomEntityList
                    .stream()
                    .filter(roomEntity1 -> Objects.equals(roomEntity1.getRoomId(), reservationEntity.getRoomId()))
                    .findFirst();
            roomEntity.ifPresent(entity -> map.put(reservationEntity, entity));
        });

        return map;
    }

    private Map<String, List<Pair<ReservationEntity, RoomEntity>>> prepareDataToGetRules() {
        List<ReservationEntity> reservationEntityList = this.reservationService.getUnCalculatedReservations();
        Map<ReservationEntity, RoomEntity> reservationToRoomMap = this.getReservationToRoomMap(reservationEntityList);
        return groupByUser(reservationToRoomMap);
    }

    private List<ReservationEntity> calculateRulesForUser(List<Pair<ReservationEntity, RoomEntity>> pairList, List<Rule> rulesFiredForUser) {
        if (Objects.isNull(pairList))
            return null;

        List<ReservationEntity> reservationEntityList = new ArrayList<ReservationEntity>();

        for(Pair<ReservationEntity, RoomEntity> pair: pairList) {
            ReservationEntity reservationEntity = pair.getKey();
            RoomEntity roomEntity = pair.getValue();

            if (Objects.nonNull(roomEntity.getRoomRules())) {
                try {
                    List<Rule> rules = deserializeListOfRules(roomEntity.getRoomRules());
                    rules.forEach(rule -> {
                        calculateRule(rule, reservationEntity);
                    });
                    rulesFiredForUser.addAll(rules);

                    reservationEntity.setCalculated(true);
                    reservationEntityList.add(reservationEntity);
                } catch (JsonProcessingException e) {
                    System.out.println("Error while getting rules for roomId: "+ roomEntity.getRoomId());
                    System.out.println(e.getMessage());
                }
            }
        }

        return reservationEntityList;
    }

    public Map<String, List<Rule>> runRules() {
        Map<String, List<Pair<ReservationEntity, RoomEntity>>> userGroups = prepareDataToGetRules();

        Map<String, List<Rule>> userToRulesMap = new HashMap<String, List<Rule>>();
        List<Rule> rulesFiredForUser = new ArrayList<Rule>();

        for (Map.Entry<String, List<Pair<ReservationEntity, RoomEntity>>> entry: userGroups.entrySet()) {
            String userId = entry.getKey();
            rulesFiredForUser = new ArrayList<Rule>();

            List<ReservationEntity> reservationEntityList = calculateRulesForUser(entry.getValue(), rulesFiredForUser);

            fireRulesForUser(userId, rulesFiredForUser);

            userToRulesMap.put(userId, rulesFiredForUser);
            this.reservationService.markReservationsAsCalculated(reservationEntityList);
        }

        return userToRulesMap;
    }

    private void fireRule(Rule rule, KieSession kieSession) {
        RuleIdEum ruleIdEum = getRoomIdEnum(rule.getRuleId());
        kieSession.insert(rule);
        kieSession.setGlobal("ruleObj", rule);
        kieSession.fireAllRules(new RulesFilter(Collections.singletonList(ruleIdEum.getDroolName())));
        System.out.println("Run rule: " + ruleIdEum.getPrettyName());
    }

    private void fireRulesForUser(String userId, List<Rule> rules) {
        Optional<UserEntity> userEntity = this.userRepository.findById(userId);
        if (userEntity.isEmpty()) {
            System.out.println("No user exists with userId: " + userId);
            return;
        }

        KieSession kieSession = kieContainer.newKieSession();
        rules.forEach(rule -> {
            fireRule(rule, kieSession);
        });
        kieSession.destroy();


        List<IncentiveEntity> allIncentives = new ArrayList<IncentiveEntity>();

        Long currentTotalIncentives = userEntity.get().getTotalIncentives();;

        for (Rule rule: rules) {
            if (rule.isFired()) {
                allIncentives.add(new IncentiveEntity(rule.getIncentive(), LocalDateTime.now(), userId, true));
                currentTotalIncentives += rule.getIncentive();
            }
        }

        this.incentiveRepository.saveAll(allIncentives);
        userEntity.get().setTotalIncentives(currentTotalIncentives);
        this.userRepository.save(userEntity.get());
    }
}
