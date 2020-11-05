package com.borodin.service_anylizer.manager;


import com.borodin.service_anylizer.model.LogEvent;
import com.borodin.service_anylizer.repository.LogEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Component
@Slf4j
public class LogAnalyzer {

    private static final Integer DIFFERENCE_VALUE_ATTACK = 10;

    @Autowired
    private LogEventRepository repository;


    public boolean analyzeIfDosAttackIsPresent() {
        LocalDateTime toDate = getToDate();
        LocalDateTime fromDate = getFromDate(toDate);
        List<LogEvent> logEvents = repository.findByDateTimeBetween(fromDate, toDate);
        Map<String, Long> resultPairs = logEvents.stream()
                .collect(Collectors.groupingBy(LogEvent::getIp, Collectors.counting()));

        Long maxNumberOfConnections = getMaxNumberOfConnectionPerIP(resultPairs);


        String ipWithMaxNumberOfRequest = getIPWithMaxNumberOfRequest(resultPairs, maxNumberOfConnections).get();

        Integer averageNumberOfRequestWithoutMax = getAverageNumberOfRequests(logEvents, ipWithMaxNumberOfRequest);
        log.info("IP with max number of Request {}", ipWithMaxNumberOfRequest);
        log.info("Average number of Request {}", averageNumberOfRequestWithoutMax);
        log.info("Max number of Request {}", maxNumberOfConnections);

        return maxNumberOfConnections > averageNumberOfRequestWithoutMax * DIFFERENCE_VALUE_ATTACK;

    }


    public boolean analyzeIfDdosAttackIsPresent() {
        LocalDateTime toDate = getToDate();
        LocalDateTime fromDate = getFromDate(toDate);
        List<Integer> numberOfVisits = getNumberOfVisitsForAPeriod(fromDate, toDate, ChronoUnit.HOURS, 24);

        int visitsForLastHour = numberOfVisits.get(23);

        numberOfVisits.remove(23);

        Integer averageNumberOfRequestWithoutMax = Double.valueOf(numberOfVisits.stream()
                .mapToDouble(Integer::doubleValue)
                .average().getAsDouble()).intValue();


        if (visitsForLastHour >= averageNumberOfRequestWithoutMax * DIFFERENCE_VALUE_ATTACK) {
            log.info("1");
            return true;
        }


        return checkIfAttackIsLongerThanDay(visitsForLastHour);

    }

    private boolean checkIfAttackIsLongerThanDay(Integer maxVisitsInDay) {
        LocalDateTime toDate = getToDate().minus(1, ChronoUnit.DAYS);
        LocalDateTime fromDate = toDate.minus(7, ChronoUnit.DAYS);
        List<Integer> numberOfVisits = getNumberOfVisitsForAPeriod(fromDate, toDate, ChronoUnit.DAYS, 7);
        numberOfVisits.remove(6);
        Integer averageNumberOfRequest = Double.valueOf(numberOfVisits.stream()
                .mapToDouble(Integer::doubleValue)
                .average().getAsDouble()).intValue();
        log.info("{}", maxVisitsInDay > averageNumberOfRequest);
        log.info("max {}", maxVisitsInDay);
        log.info("average {}", averageNumberOfRequest);
        return maxVisitsInDay > averageNumberOfRequest * 10;

    }

    private List<Integer> getNumberOfVisitsForAPeriod(LocalDateTime toDate, LocalDateTime fromDate, ChronoUnit days, int splitValue) {
        List<LogEvent> logEvents = getSortedListWithEventDuringTime(fromDate, toDate);

        List<List<LogEvent>> listWithPeriods = splitListToVisitPerOur(logEvents, days, splitValue);

        return listWithPeriods.stream().map(List::size).collect(Collectors.toList());
    }

    public List<Integer> getNumberOfVisitsForYear() {
        LocalDateTime toDate = getToDate();
        LocalDateTime fromDate = toDate.minus(1, ChronoUnit.YEARS);
        return getNumberOfVisitsForAPeriod(fromDate, toDate, ChronoUnit.MONTHS, 12);
    }

    public List<Integer> getNumberOfVisitsForMonth() {
        LocalDateTime toDate = getToDate();
        LocalDateTime fromDate = toDate.minus(1, ChronoUnit.MONTHS);


        return getNumberOfVisitsForAPeriod(fromDate, toDate,
                ChronoUnit.DAYS,
                Long.valueOf(Duration.between(fromDate, toDate).toDays()).intValue());
    }

    public List<Integer> getNumberOfVisitsForDay() {
        LocalDateTime toDate = getToDate();
        LocalDateTime fromDate = toDate.minus(1, ChronoUnit.DAYS);
        return getNumberOfVisitsForAPeriod(fromDate, toDate,
                ChronoUnit.HOURS,
                24);
    }


    public List<List<LogEvent>> splitListToVisitPerOur(List<LogEvent> logEvents, ChronoUnit chronoUnit, Integer splitValue) {
        List<List<LogEvent>> listWithPeriods = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger(splitValue);
        while (atomicInteger.get() >= 1) {
            listWithPeriods.add(logEvents.stream()
                    .filter(e -> e.getDateTime().isAfter(LocalDateTime.now().minus(atomicInteger.get(), chronoUnit))
                            && e.getDateTime().isBefore(LocalDateTime.now().minus(atomicInteger.get() - 1, chronoUnit)
                    ))
                    .collect(Collectors.toList()));
            atomicInteger.getAndDecrement();
        }
        return listWithPeriods;
    }

    private List<LogEvent> getSortedListWithEventDuringTime(LocalDateTime toDate, LocalDateTime fromDate) {
        return new ArrayList<>(repository.findByDateTimeBetween(fromDate, toDate));
    }

    private Long getMaxNumberOfConnectionPerIP(Map<String, Long> resultPairs) {
        return resultPairs.values().stream()
                .max(Comparator.comparing(Long::valueOf))
                .get();
    }

    private Optional<String> getIPWithMaxNumberOfRequest(Map<String, Long> resultPairs, Long maxNumberOfConnections) {
        return resultPairs.entrySet()
                .stream()
                .filter(entry -> maxNumberOfConnections.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private Integer getAverageNumberOfRequests(List<LogEvent> logEvents, String result) {
        return Double.valueOf(logEvents.stream()
                .filter(e -> !e.getIp().equals(result))
                .collect(Collectors.groupingBy(LogEvent::getIp, Collectors.counting())).values()
                .stream()
                .mapToDouble(Long::doubleValue)
                .average().orElse(Double.NaN)).intValue();
    }

    private Integer getAverageNumberOfRequests(List<LogEvent> logEvents) {
        return Double.valueOf(logEvents.stream()
                .collect(Collectors.groupingBy(LogEvent::getIp, Collectors.counting())).values()
                .stream()
                .mapToDouble(Long::doubleValue)
                .average().orElse(Double.NaN)).intValue();
    }

    private LocalDateTime getFromDate(LocalDateTime toDate) {
        return toDate.minus(1, ChronoUnit.DAYS);
    }

    private LocalDateTime getToDate() {
        return LocalDateTime.now();
    }


}
