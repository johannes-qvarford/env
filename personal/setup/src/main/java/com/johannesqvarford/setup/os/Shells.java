package com.johannesqvarford.setup.os;

import java.io.IOException;

public class Shells
{
    private Shells()
    {
    }

    public static void call(String commandline) throws IOException, InterruptedException
    {
        Process process = 
            new ProcessBuilder(new String[] {"bash", "-c", commandline})
                .redirectErrorStream(true)
                .directory(FileUtils.homeDirectory().toFile())
                .start();
        process.wait();
    }

    public static void callf(String commandline, Object... args) throws IOException, InterruptedException
    {
        call(String.format(commandline, args));
    }
}