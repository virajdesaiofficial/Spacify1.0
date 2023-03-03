package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyLib.dto.RulesRequest;
import org.uci.spacifyPortal.services.RuleService;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @PostMapping("/addRules")
    public ResponseEntity<String> addRules(@RequestBody RulesRequest request) throws Exception {
        // Call the rule service with the provided data
        this.ruleService.addRules(request.getRoomId(), request.getUserId(), request.getRules());

        return new ResponseEntity<>("Room created successfully", HttpStatus.CREATED);
    }
}
