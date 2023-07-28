package com.highonmusic.apiGateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class GatewayController {

    private final LoadBalancerClient loadBalancerClient;

    public GatewayController(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    @GetMapping("")
    public String test(){
        return "Api gateway test";
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/songs")
    public ResponseEntity<String> callSongs() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://highonmusic-songs/songs", String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            // Handle error case
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
        }
    }

}
