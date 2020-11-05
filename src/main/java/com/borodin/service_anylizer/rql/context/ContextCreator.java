package com.borodin.service_anylizer.rql.context;


import com.borodin.service_anylizer.rql.antlr.RQLBaseListener;
import com.borodin.service_anylizer.rql.antlr.RQLParser;

public class ContextCreator extends RQLBaseListener {


    private MainQueryContext mainQueryContext;


    public ContextCreator(MainQueryContext mainQueryContext) {
        this.mainQueryContext = mainQueryContext;
    }


    @Override
    public void exitDateAndTime(RQLParser.DateAndTimeContext ctx) {

        mainQueryContext.setDateAndTimeContext(ctx);

        super.exitDateAndTime(ctx);
    }

    @Override
    public void exitIp(RQLParser.IpContext ctx) {
        mainQueryContext.setIpContext(ctx);
        super.exitIp(ctx);
    }


}


