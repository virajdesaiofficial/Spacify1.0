package org.uci.spacifyEngine.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spacifyEngine")
public class SpacifyEngine {
    @GetMapping("/test")
    @ResponseBody
    public String dummyController() {
        return "alive";
    }
}
