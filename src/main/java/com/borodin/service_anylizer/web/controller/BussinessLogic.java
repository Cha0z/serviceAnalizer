package com.borodin.service_anylizer.web.controller;


import com.borodin.service_anylizer.manager.LogAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BussinessLogic {



    @Autowired
    private LogAnalyzer logAnalyzer;


    @PutMapping("/start")
    public void businessLogic(@RequestHeader("Status") String status) {

       log.info("Dos {}", logAnalyzer.analyzeIfDosAttackIsPresent());
        log.info("Ddos {}",logAnalyzer.analyzeIfDdosAttackIsPresent());
    }





}
