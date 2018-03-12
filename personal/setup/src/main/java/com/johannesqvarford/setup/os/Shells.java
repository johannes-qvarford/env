package com.johannesqvarford.setup.os;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public class Shells
{
    private Shells()
    {
    }

    public static void call(String commandline) throws IOException, InterruptedException
    {
        System.out.printf("<call>: %s%n", commandline);
        Process process = 
            new ProcessBuilder(new String[] {"bash", "-c", commandline})
                .redirectErrorStream(true)
                .redirectError(Redirect.INHERIT)
                .redirectOutput(Redirect.INHERIT)
                .redirectInput(Redirect.INHERIT)
                .directory(FileUtils.homeDirectory().toFile())
                .start();
        System.out.printf("<output>: %s%n", IOUtils.toString(process.getInputStream(), Charset.forName("UTF-8")));
        process.waitFor();
    }

    public static void callf(String commandline, Object... args) throws IOException, InterruptedException
    {
        call(String.format(commandline, args));
    }
}