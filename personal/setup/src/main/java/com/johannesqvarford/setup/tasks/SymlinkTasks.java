package com.johannesqvarford.setup.tasks;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.johannesqvarford.setup.env.Environment;
import com.johannesqvarford.setup.os.FileUtils;
import static com.johannesqvarford.setup.os.FileUtils.homeDirectory;

public class SymlinkTasks
{
    private final List<String> DOTFILES_SUBDIRECTORIES_TO_IGNORE = Arrays.asList(".hg", ".DS_STORE");

    private SymlinkTasks()
    {
    }

    public static void symlinkDotFiles(Path repositoryDirectory) throws IOException
    {
        // We want every regular file in dotfiles to be a symlink,
        // but we don't want .config to be a link, to support npm on windows that access that directory.
        // We can instead link all files inside .config
        symlinkFiles(
            repositoryDirectory.resolve("dotfiles"),
            homeDirectory(),
            ".m2", ".config", ".DS_Store");
        symlinkFiles(
            repositoryDirectory.resolve("dotfiles").resolve(".config"),
            homeDirectory().resolve(".config"));
        symlinkFiles(
            repositoryDirectory.resolve("dotfiles").resolve(".m2"),
            homeDirectory().resolve(".m2"),
            "repository");
    }

    public static void symlinkBinFiles(Path repositoryDirectory) throws IOException
    {
        Path sourceDirectory = repositoryDirectory.resolve("bin");
        Path destinationDirectory = homeDirectory().resolve("bin");
        symlinkFiles(sourceDirectory, destinationDirectory);
    }

    public static void symlinkVSCodeFiles(Path repositoryDirectory) throws IOException
    {
        Path sourceDirectory = repositoryDirectory.resolve("vscode");
        Path destinationDirectory = Environment.current().vscodeSettingsDirectory();
        destinationDirectory.toFile().mkdirs();
        symlinkFiles(sourceDirectory, destinationDirectory, "workspaceStorage", ".DS_Store");
    }

    private static void symlinkFiles(Path sourceDirectory, Path destinationDirectory, String... subDirectoriesToAvoid) throws IOException
    {
        Path homeDotfilesDirectory = destinationDirectory;
        Path repositoryDotfilesDirectory = sourceDirectory;

        List<String> toAvoid = Arrays.asList(subDirectoriesToAvoid);
        List<Path> dotfilePaths = FileUtils
            .nonRecursiveGetChildPaths(repositoryDotfilesDirectory);
        dotfilePaths = dotfilePaths
            .stream()
            .filter(p -> toAvoid.stream().noneMatch(sd -> p.toString().toLowerCase().contains(sd.toLowerCase())))
            .collect(Collectors.toList());

        for (Path dotfilePath : dotfilePaths)
        {
            Path relativePath = repositoryDotfilesDirectory.relativize(dotfilePath);
            Path homeFilePath = homeDotfilesDirectory.resolve(relativePath);
            FileUtils.symlink(dotfilePath, homeFilePath);
        }
    }
}