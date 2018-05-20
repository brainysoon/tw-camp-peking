package com.example.peking.controller;

import com.example.peking.constant.UriConstants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HubController {

    @GetMapping(UriConstants.INDEX)
    HttpEntity<String> index() {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
