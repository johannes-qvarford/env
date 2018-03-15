package com.johannesqvarford.server;

public class Response
{
    private final String stdout;

    private final String stderr;

    private final int exitStatus;

    public Response(String stdout, String stderr, int exitStatus)
    {
        this.stderr = stderr;
        this.stdout = stdout;
        this.exitStatus = exitStatus;
    }

    public static Response exception(Exception e)
    {
        return new Response("", e.toString(), 2);
    }

    public static Response success()
    {
        return new Response("", "", 0);
    }

    public static Response success(String stdout)
    {
        return new Response(stdout, "", 0);
    }

    public static Response ofExitStatus(int exitStatus)
    {
        return new Response("", "", exitStatus);
    }

    public String getStdout()
    {
        return stdout;
    }

    public String getStderr()
    {
        return stderr;
    }

    public int getExitStatus()
    {
        return exitStatus;
    }
}