package com.johannesqvarford.appcore;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ParserProperties;
import org.kohsuke.args4j.ExampleMode;

public abstract class CoreApp<T>
{
    public abstract Class<T> optionsClass();

    public abstract String commandName();
    
    public abstract void run(T options) throws Exception;

    public static <T> void main(CoreApp<T> app, String[] args) throws Exception
    {
        app.doMain(args);
    }

    private void doMain(String[] args) throws Exception
    {
        T options = optionsClass().newInstance();
        CmdLineParser parser = new CmdLineParser(options, ParserProperties.defaults().withUsageWidth(80));
        try {
            parser.parseArgument(args);
            run(options);
        } catch(CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println(String.format("%s [options...] arguments...", commandName()));

            parser.printUsage(System.err);
            System.err.println();

            // print option sample. This is useful some time
            System.err.printf("  Example: %s %s%n", commandName(), parser.printExample(ExampleMode.ALL));

            return;
        }
    }
}