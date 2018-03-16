package com.johannesqvarford.setup.tasks;

import static com.johannesqvarford.setup.os.FileUtils.homeDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.johannesqvarford.setup.os.Shells;

import org.eclipse.jgit.api.Git;

public class ApplicationTasks
{
    public static void installVim() throws IOException, InterruptedException
    {
        Path vimDirectory = Paths.get(homeDirectory().toString(), ".vim", "dein", "repos", "github.com", "Shougo", "dein.vim");
        vimDirectory.toFile().mkdir();
        
        Git.cloneRepository()
            .setURI("https://github.com/Shougo/dein.vim.git")
            .setDirectory(vimDirectory.toFile());

        Shells.call("vim +'call dein#install()' +qall");
        Shells.call("vim +'call dein#install()' +qall");
    }

    public static void convertVimFilesToUnix() throws IOException, InterruptedException
    {
        Shells.callf("find %s/.vim -type f -print0 | xargs -0 dos2unix", homeDirectory());
        Shells.call("vim +PluginInstall +qall");
    }

    public static void changeShell(String path) throws IOException, InterruptedException
    {
        Shells.callf("echo %s | sudo tee -a /etc/shells", path);
        Shells.callf("chsh -s %s", path);
    }
}