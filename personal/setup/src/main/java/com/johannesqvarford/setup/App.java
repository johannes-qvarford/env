package com.johannesqvarford.setup;

import static org.kohsuke.args4j.ExampleMode.ALL;

import java.io.IOException;

import com.johannesqvarford.setup.env.Environment;
import com.johannesqvarford.setup.opt.Options;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.ParserProperties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        App app = new App();
        app.doMain(args);
    }

    public void doMain(String[] args) throws IOException, InterruptedException
    {
        Options options = new Options();
        CmdLineParser parser = new CmdLineParser(options, ParserProperties.defaults().withUsageWidth(80));
        
        try {
            parser.parseArgument(args);
            Environment.current().execute(options);
        } catch(CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java App [options...] arguments...");

            parser.printUsage(System.err);
            System.err.println();

            // print option sample. This is useful some time
            System.err.println("  Example: java App"+parser.printExample(ALL));

            return;
        }
    }
}
