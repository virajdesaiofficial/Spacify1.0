package org.uci.spacify.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacify.Services.AccountService;

@RestController
@RequestMapping("/spacify/login")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/test")
    @ResponseBody
    public ResponseEntity<String> testMethod() {
        return new ResponseEntity<String>(accountService.testMethod(), HttpStatus.OK);

    }
}
