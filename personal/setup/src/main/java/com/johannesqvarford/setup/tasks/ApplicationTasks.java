package com.johannesqvarford.setup.tasks;

import static com.johannesqvarford.setup.os.FileUtils.homeDirectory;

import java.io.IOException;
import java.nio.file.Path;
import com.johannesqvarford.setup.os.Shells;

import org.eclipse.jgit.api.Git;

public class ApplicationTasks
{
    public static void installVim() throws IOException, InterruptedException
    {
        Path vimDirectory = homeDirectory().resolve(".vim");
        vimDirectory.toFile().mkdir();
        
        Path vundleDirectory = vimDirectory.resolve("bundle").resolve("Vundle.vim");
        Git.cloneRepository()
            .setURI("https://github.com/VundleVim/Vundle.vim.git")
            .setDirectory(vundleDirectory.toFile());

        Shells.call("vim +PluginInstall +qall");
        Shells.call("vim +PluginInstall +qall");
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