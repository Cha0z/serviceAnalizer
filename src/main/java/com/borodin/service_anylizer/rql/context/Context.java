package com.borodin.service_anylizer.rql.context;


import com.borodin.service_anylizer.rql.antlr.RQLParser;

public interface Context {

    default RQLParser.IpContext getIPContext(){return null;}
    default RQLParser.DateAndTimeContext getDateAndTimeTerm(){return null;}

}
