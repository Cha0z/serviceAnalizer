package com.borodin.service_anylizer.rql.processor;


import com.borodin.service_anylizer.model.LogEvent;
import com.borodin.service_anylizer.rql.context.MainQueryContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class ParserProcessor {
    private MainQueryContext context;


    public ParserProcessor(MainQueryContext queryContext) {
        context = queryContext;
    }


    public LogEvent getLogEvent() {

        LogEvent logEvent = createNewInstanceOfEvent();
        logEvent.setIp(transformIPFromContextToString());
        logEvent.setDateTime(transformDateAndTimeFromContextToString());
        return logEvent;


    }

    private String transformIPFromContextToString() {
        StringBuilder textFormat = new StringBuilder();
        context.getIpContext().children.forEach(textFormat::append);
        return textFormat.toString();
    }

    private LocalDateTime transformDateAndTimeFromContextToString() {
        StringBuilder textFormat = transformDateFromContextToString();

        return LocalDateTime.parse(textFormat, DateTimeFormatter.ofPattern("yyyy'/'MM'/'dd':'HH':'mm':'ss", Locale.US));
    }

    private StringBuilder transformDateFromContextToString() {
        StringBuilder textFormat = new StringBuilder();
        context.getDateAndTimeContext().children.forEach(child -> textFormat.append(child.getText()));
        return textFormat;
    }

    private LogEvent createNewInstanceOfEvent() {
        return new LogEvent();
    }
}
