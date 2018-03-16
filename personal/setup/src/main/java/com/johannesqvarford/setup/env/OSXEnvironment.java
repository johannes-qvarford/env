package com.johannesqvarford.setup.env;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.johannesqvarford.setup.opt.Options;
import com.johannesqvarford.setup.os.FileUtils;
import com.johannesqvarford.setup.os.Shells;
import com.johannesqvarford.setup.tasks.ApplicationTasks;
import com.johannesqvarford.setup.tasks.SymlinkTasks;

public class OSXEnvironment implements Environment
{
    @Override
    public void execute(Options options) throws IOException, InterruptedException {
        
        if (options.installDependencies())
        {
            installPackages("dos2unix", "fish", "fzf", "jq", "tmux");
            Shells.call("curl -Lo ~/.config/fish/functions/fisher.fish --create-dirs https://git.io/fisher");
            Shells.call("fisher fzf");
        }

        if (options.changeShell())
        {
            ApplicationTasks.changeShell("/usr/local/bin/fish");
        }

        if (options.initializeVim())
        {
            ApplicationTasks.installVim();
            ApplicationTasks.convertVimFilesToUnix();
        }

        if (options.linkFiles())
        {
            SymlinkTasks.symlinkDotFiles(options.repositoryDirectory());
            SymlinkTasks.symlinkBinFiles(options.repositoryDirectory());
            SymlinkTasks.symlinkVSCodeFiles(options.repositoryDirectory());
        }

        Shells.call("defaults write com.apple.finder AppleShowAllFiles YES");
    }

	@Override
	public Path vscodeSettingsDirectory() {
        return Paths.get(FileUtils.homeDirectory().toString(), "Library", "Application Support", "Code", "User");
	}

	@Override
	public void installPackages(String... packageNames) throws IOException, InterruptedException {
        Shells.call(String.format("brew install %s", String.join(" ", packageNames)));
    }
}