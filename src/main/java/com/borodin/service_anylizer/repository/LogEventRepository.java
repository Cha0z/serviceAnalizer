package com.borodin.service_anylizer.repository;

import com.borodin.service_anylizer.model.LogEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogEventRepository extends JpaRepository<LogEvent,Long> {

    List<LogEvent> findByDateTimeBetween(LocalDateTime firstDate,LocalDateTime secondDate);


}
