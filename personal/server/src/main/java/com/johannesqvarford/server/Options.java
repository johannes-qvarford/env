package com.johannesqvarford.server;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ParserProperties;

public class Options
{
    private Options()
    {
    }

    public static <T> T parse(String[] args, Class<T> cls) throws InstantiationException, IllegalAccessException, CmdLineException
    {
        T options = cls.newInstance();
        CmdLineParser parser = new CmdLineParser(options, ParserProperties.defaults().withUsageWidth(80));
        parser.parseArgument(args);
        return options;
    }
}