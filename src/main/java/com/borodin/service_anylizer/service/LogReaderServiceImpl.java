package com.borodin.service_anylizer.service;

import com.borodin.service_anylizer.model.LogEvent;
import com.borodin.service_anylizer.rql.antlr.RQLLexer;
import com.borodin.service_anylizer.rql.antlr.RQLParser;
import com.borodin.service_anylizer.rql.context.ContextCreator;
import com.borodin.service_anylizer.rql.context.MainQueryContext;
import com.borodin.service_anylizer.rql.processor.ParserProcessor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class LogReaderServiceImpl implements LogReaderService {

    //"192.168.1.2 - - [2013/Sep/17:22:18:19]"
    @Override
    public LogEvent transformTextToObj(String log) {
        RQLLexer lexer = new RQLLexer(CharStreams.fromString(log));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        RQLParser parser = new RQLParser(tokens);
        ParseTreeWalker walker = new ParseTreeWalker();
        MainQueryContext mainQueryContext = new MainQueryContext();
        ContextCreator contextCreator = new ContextCreator(mainQueryContext);
        walker.walk(contextCreator, parser.log());
        ParserProcessor parserProcessor = new ParserProcessor(mainQueryContext);
        return parserProcessor.getLogEvent();
    }
}
