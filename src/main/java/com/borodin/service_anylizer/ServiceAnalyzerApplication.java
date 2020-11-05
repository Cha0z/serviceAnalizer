package com.borodin.service_anylizer;

import com.borodin.service_anylizer.manager.LogManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@SpringBootApplication
@Slf4j
public class ServiceAnalyzerApplication implements
        ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private LogManager logManager;

    public static void main(String[] args) {
        SpringApplication.run(ServiceAnalyzerApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

//        fillTextFile();
//        log.info("filling file ended");
//        logManager.pushLogsToBD();
    }


    private void fillTextFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/eventLogs.txt"), 1024)) {
            for (int i = 0; i < 10000; i++) {
                writer.write(genIP() + " - - [" + getDateTime() + "] \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDateTime() {
        Integer value = Double.valueOf(1 + Math.random()*525600).intValue();
        LocalDateTime dateTime = LocalDateTime.now().minus(value, ChronoUnit.MINUTES);
//		LocalDateTime dateTime = LocalDateTime.now().minus((Double.valueOf(random.nextGaussian()*10000).intValue()), ChronoUnit.MINUTES);

        return DateTimeFormatter.ofPattern("yyyy'/'MM'/'dd':'HH':'mm':'ss", Locale.US).format(dateTime);
    }

    private String genIP() {
        Integer start = 1;
        Integer end = 254;

        return String.format("%s.%s.%s.%s", (Double.valueOf(start + (Math.random() * end)).intValue()),
                (Double.valueOf(start + (Math.random() * end)).intValue()),
                (Double.valueOf(start + (Math.random() * end)).intValue()),
                (Double.valueOf(start + (Math.random() * end)).intValue()));
    }
}
