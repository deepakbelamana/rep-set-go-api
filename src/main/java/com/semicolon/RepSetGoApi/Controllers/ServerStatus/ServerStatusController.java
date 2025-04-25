package com.semicolon.RepSetGoApi.Controllers.ServerStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/rep-set-go/status")
public class ServerStatusController {

    @GetMapping()
    public ResponseEntity checkStatus() {
        return ResponseEntity.ok().body("server is up and running..!");
    }
}
