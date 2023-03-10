package org.uci.spacifyEngine.controllers;


import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyEngine.services.RulesFilter;
import org.uci.spacifyLib.dto.RulesTBDeleted;


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


    @PostMapping("/calculateIncentives")
    @ResponseBody
    public RulesTBDeleted incentiveCredited(@RequestBody RulesTBDeleted rule) {
        List<String> listOfRules = new ArrayList<>();
        listOfRules.add("occupancy rule");
        listOfRules.add("duration rule");
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(rule);
        kieSession.setGlobal("ruleObj", rule);
        kieSession.fireAllRules(new RulesFilter(listOfRules));
        return rule;
    }
}
