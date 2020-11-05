package com.borodin.service_anylizer.service;

import com.borodin.service_anylizer.model.LogEvent;

public interface LogReaderService {


    LogEvent transformTextToObj(String log);

}
