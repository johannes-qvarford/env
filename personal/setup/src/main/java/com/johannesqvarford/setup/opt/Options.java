package com.johannesqvarford.setup.opt;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.kohsuke.args4j.Option;

public class Options
{
    @Option(name="-r", aliases = {"--repository-directory"}, usage="the root directory of the env repository", metaVar="DIR")
    private Path repositoryDirectory = Paths.get(System.getProperty("user.dir"), "env2");

    @Option(name="-i", aliases = {"--install-dependencies"}, usage="whether or not to install dependencies during this run")
    private boolean installDependencies = false;

    @Option(name="-v", aliases= {"--initialize-vim"}, usage="whether or not to initialize vim files during this run")
    private boolean initializeVim = false;

    @Option(name="-l", aliases= {"--link-files"}, usage="whether or not to link home and repository files")
    private boolean linkFiles = false;

    @Option(name="-s", aliases= {"--change-shell"}, usage="whether or not to change the shell")
    private boolean changeShell = false;

    public Path repositoryDirectory()
    {
        return repositoryDirectory;
    }

    public boolean installDependencies()
    {
        return installDependencies;
    }

    public boolean initializeVim()
    {
        return initializeVim;
    }

    public boolean linkFiles()
    {
        return linkFiles;
    }

    public boolean changeShell()
    {
        return changeShell;
    }
}