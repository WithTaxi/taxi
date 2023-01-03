package com.withtaxi.taxi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @GetMapping("/success")
    public ResponseEntity success() {
        log.info("로그인 성공");
        Map<String, Object> map = new HashMap<>();
        map.put("result", 1);
        return new ResponseEntity(map, HttpStatus.OK);
    }

    @GetMapping("/fail")
    public ResponseEntity fail() {
        log.info("로그인 실패");
        Map<String, Object> map = new HashMap<>();
        map.put("result", 0);
        return new ResponseEntity(map, HttpStatus.OK);
    }
}