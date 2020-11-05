package com.borodin.service_anylizer.webpage.helper;


import com.borodin.service_anylizer.webpage.model.ActuatorStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CheckWebStatusHelper {

    private static final String URL = "http://localhost:8080/actuator/health";

    @Autowired
    private RestTemplate restTemplate;

    public String checkStatus(){
        return restTemplate.exchange(URL, HttpMethod.GET, null, ActuatorStatus.class).getBody().getStatus();
    }

}
