package org.uci.spacifyEngine.controllers;


import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyEngine.services.AvailableSlotsService;
import org.uci.spacifyEngine.services.ReservationService;
import org.uci.spacifyEngine.services.RulesFilter;
import org.uci.spacifyLib.dto.Rules;
import org.uci.spacifyLib.entity.AvailableSlotsEntity;
import org.uci.spacifyLib.entity.ReservationEntity;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/spacifyEngine")
public class SpacifyEngine {

    @Autowired
    private KieContainer kieContainer;

    @GetMapping("/test")
    @ResponseBody
    public String dummyController() {
        return "alive";
    }

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/all")
    public List<ReservationEntity> getAllReservations() {
        return this.reservationService.getAllReservations();
    }

    @Autowired
    private AvailableSlotsService availableSlotsService;

    @GetMapping("/availableSlots")
    public List<ReservationEntity> getAllAvailableSlots() {
        return this.availableSlotsService.getAllAvailableSlots();
    }

    @PostMapping("/calculateIncentives")
    @ResponseBody
    public Rules incentiveCredited(@RequestBody  Rules rule) {
        List<String> listOfRules = new ArrayList<>();
        listOfRules.add("occupancy rule");
//        listOfRules.add("duration rule");
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(rule);
        kieSession.setGlobal("ruleObj", rule);
        kieSession.fireAllRules(new RulesFilter(listOfRules));
        return rule;
    }
}
