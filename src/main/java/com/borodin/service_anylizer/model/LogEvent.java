package com.borodin.service_anylizer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class LogEvent {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String ip;
    private LocalDateTime dateTime;

}
