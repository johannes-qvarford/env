package com.johannesqvarford.gitisdirty;

import com.johannesqvarford.appcore.CoreApp;
import java.io.File;
import org.apache.log4j.BasicConfigurator;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

public class App extends CoreApp<Options>
{
    @Override
    public Class<Options> optionsClass()
    {
        return Options.class;
    }

    @Override
    public String commandName()
    {
        return "git-is-dirty";
    }  

    public static void main(String[] args) throws Exception
    {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
        BasicConfigurator.configure();
        CoreApp.main(new App(), args);
    }

    @Override
    public void run(Options options) throws Exception
    {
        try (
            Repository repository = new RepositoryBuilder().findGitDir().build();
            Git git = Git.wrap(repository))
        {
            Status status = git.status().call();
            System.exit(status.isClean() ? 1 : 0);
        }
    }
}
