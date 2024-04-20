package com.server.salpinoffServer.infra.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfraController {

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/redirect")
    public ResponseEntity<String> kakaoLoginRedirect(@RequestParam("code") String code) {
        System.out.println(code);

        return ResponseEntity.ok(code);
    }

}
