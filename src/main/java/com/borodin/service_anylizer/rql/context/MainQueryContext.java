package com.borodin.service_anylizer.rql.context;

import com.borodin.service_anylizer.rql.antlr.RQLParser;
import lombok.Data;

@Data
public class MainQueryContext implements Context {
//    private List<QueryContext> queryContextList;

   private RQLParser.IpContext ipContext;
   private RQLParser.DateAndTimeContext dateAndTimeContext;



}
