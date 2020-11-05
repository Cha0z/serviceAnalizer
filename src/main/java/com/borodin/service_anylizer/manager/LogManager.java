package com.borodin.service_anylizer.manager;


import com.borodin.service_anylizer.model.LogEvent;
import com.borodin.service_anylizer.repository.LogEventRepository;
import com.borodin.service_anylizer.service.LogReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
@Slf4j
public class LogManager {

    private final static String PATH = "src/main/resources/eventLogs.txt";

    @Autowired
    private LogReaderService logReaderService;
    @Autowired
    private LogEventRepository repository;


    public void pushLogsToBD() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH), 1024)) {
            log.info("Start reading file");
            addLogsToBd(reader);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private void addLogsToBd(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            repository.save(logReaderService.transformTextToObj(line));
        }
    }

}
