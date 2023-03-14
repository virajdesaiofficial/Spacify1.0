package org.uci.spacifyEngine.controllers;


import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyEngine.services.RuleRunService;
import org.uci.spacifyEngine.services.RulesFilter;
import org.uci.spacifyLib.dto.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ruleCalculation")
public class RuleCalculation {

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private RuleRunService ruleRunService;

    @GetMapping("/test")
    @ResponseBody
    public String dummyController() {
        return "alive";
    }


    @PostMapping("/calculateIncentives")
    @ResponseBody
    public Rule incentiveCredited(@RequestBody Rule rule) {
        List<String> listOfRules = new ArrayList<>();
        listOfRules.add("occupancy rule");
        listOfRules.add("duration rule");
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(rule);
        kieSession.setGlobal("ruleObj", rule);
        kieSession.fireAllRules(new RulesFilter(listOfRules));
        return rule;
    }

    @GetMapping("/manualTrigger")
    @ResponseBody
    public Map<String, List<Rule>> manuallyTriggerRules() {
        return this.ruleRunService.runRules();
    }
}
